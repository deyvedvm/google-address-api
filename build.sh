#!/usr/bin/env bash

docker build -t google-address-api:1.0 .

docker run -it --rm -d -p 8080:8080 -t google-address-api:1.0