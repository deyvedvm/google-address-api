FROM openjdk:11-jdk as build

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests

FROM openjdk:11.0.7-jre-slim

MAINTAINER Deyve Vieira <dvmachado@id.uff.br>

EXPOSE 8080

WORKDIR /workspace/app

#Possibility to set JVM options (https://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html)
ENV JAVA_OPTS="-Dspring.profiles.active=prod"

COPY --from=build /workspace/app/target/*.jar app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar

