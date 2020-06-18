package dev.deyve.googleaddressapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.deyve.googleaddressapi.models.Address;
import org.bson.types.ObjectId;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@ActiveProfiles("test")
public class TestUtil {

    public static Address buildAddressResult(ObjectMapper objectMapper, MvcResult result) throws JsonProcessingException, UnsupportedEncodingException {
        String contentAsString = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        return objectMapper.readValue(contentAsString, Address.class);
    }

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
