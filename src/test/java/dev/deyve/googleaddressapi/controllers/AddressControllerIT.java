package dev.deyve.googleaddressapi.controllers;

import dev.deyve.googleaddressapi.GoogleAddressApiApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static dev.deyve.googleaddressapi.utils.TestUtil.buildAddress;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@DataMongoTest
//@SpringBootTest
@ActiveProfiles("test")
@SpringBootTest(classes = GoogleAddressApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressControllerIT {

    @LocalServerPort
    private int port;

    private final String URL = "http://localhost:8080/api/address";

//    @Autowired
//    AddressService addressService;

    @Autowired
    private TestRestTemplate restTemplate;

//    @Autowired
//    AddressRepository addressRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("GET Address - should return all addresses")
    public void testAllAddresses() {
        mongoTemplate.save(buildAddress());

        List addressList = this.restTemplate
                .getForObject("http://localhost:" + port + "/api/address", List.class);

        System.out.println(addressList);

        assertEquals(1, addressList.size());
    }


    /*@Test
    @DisplayName("GET Address - should return all addresses")
    void WhenRequestGetAddressesShouldReturnAllAddressTest() {

        List<Address> addresses = new ArrayList<>();

        given()
                .when().get(URL)
                .then()
                .statusCode(200)
                .body(is(addresses));

    }*/
}
