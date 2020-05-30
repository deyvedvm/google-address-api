package dev.deyve.googleaddressapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    String id;

    String streetName;

    String number;

    String complement;

    String neighbourhood;

    String city;

    String state;

    String country;

    String zipcode;

    String latitude;

    String longitude;
}
