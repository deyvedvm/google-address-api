package dev.deyve.googleaddressapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data

@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Indexed(unique = true)
    String id;

    @NotNull(message = "StreetName not be null")
    String streetName;

    @NotNull(message = "Number not be null")
    String number;

    @NotNull(message = "Complement not be null")
    String complement;

    @NotNull(message = "Neighbourhood not be null")
    String neighbourhood;

    @NotNull(message = "City not be null")
    String city;

    @NotNull(message = "State not be null")
    String state;

    @NotNull(message = "Country not be null")
    String country;

    @NotNull(message = "ZipCode not be null")
    String zipcode;

    Double latitude;

    Double longitude;
}
