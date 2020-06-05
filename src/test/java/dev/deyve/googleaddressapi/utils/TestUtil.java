package dev.deyve.googleaddressapi.utils;

import dev.deyve.googleaddressapi.models.Address;
import org.bson.types.ObjectId;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class TestUtil {

    public static Address buildAddress() {
        return Address.builder()
                .streetName("Av. Barão de Tefé")
                .number("27")
                .complement("")
                .neighbourhood("Saúde")
                .city("Rio de Janeiro")
                .state("RJ")
                .country("Brazil")
                .zipcode("20220-460")
                .build();
    }

    public static Address buildAddressMock() {
        return Address.builder()
                .id(ObjectId.get().toString())
                .streetName("Av. Barão de Tefé")
                .number("27")
                .complement("")
                .neighbourhood("Saúde")
                .city("Rio de Janeiro")
                .state("RJ")
                .country("Brazil")
                .zipcode("20220-460")
                .latitude(-22.8962282)
                .longitude(-43.1866427)
                .build();
    }
}
