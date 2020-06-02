package dev.deyve.googleaddressapi.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.deyve.googleaddressapi.models.Address;
import dev.deyve.googleaddressapi.services.AddressService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@WebMvcTest(controllers = AddressController.class)
class AddressControllerTest {

    private final String URL = "http://localhost:8080/api/address";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("GET Address - should return all addresses")
    void WhenRequestGetAddressesShouldReturnAllAddressTest() throws Exception {
        Address addressMock = buildAddressMock();

        List<Address> addressListMock = Collections.singletonList(addressMock);

        when(addressService.getAddresses()).thenReturn(addressListMock);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(addressService).getAddresses();

        String contentAsString = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Address> addressList = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        assertEquals(addressList, addressListMock, "Incorrect Response content");
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus(), "Incorrect Response Status");
    }

    @Test
    @DisplayName("POST Address - should save address - return address saved")
    void WhenRequestPostAddressShouldSaveAddressTest() throws Exception {
        Address address = buildAddress();

        Address addressMock = buildAddressMock();

        when(addressService.saveAddress(any(Address.class))).thenReturn(addressMock);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .content(objectMapper.writeValueAsString(address))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(addressService).saveAddress(any(Address.class));

        String contentAsString = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Address addressResult = objectMapper.readValue(contentAsString, Address.class);

        assertEquals(addressMock, addressResult, "Incorrect Response content");
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus(), "Incorrect Response Status");
    }

    @Test
    @DisplayName("PUT Address - should save address - return address updated ")
    void WhenRequestPutAddressShouldUpdatedAddressTest() throws Exception {

    }

    @Test
    @DisplayName("GET Address by id - should return address by id")
    void WhenRequestGetAddressByIdShouldReturnAddressTest() throws Exception {
        String idMock = ObjectId.get().toString();

        Address addressMock = buildAddressMock();

        when(addressService.findAddressById(idMock)).thenReturn(addressMock);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", idMock)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(addressService).findAddressById(idMock);

        String contentAsString = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Address address = objectMapper.readValue(contentAsString, Address.class);

        assertEquals(address, addressMock, "Incorrect Response content");
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus(), "Incorrect Response Status");
    }

    @Test
    @DisplayName("GET Address by id - given id that do not exist - should return not found")
    void WhenRequestGetAddressByIdShouldReturnNotFoundTest() throws Exception {
        String idMock = ObjectId.get().toString();

        when(addressService.findAddressById(idMock)).thenReturn(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", idMock)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(addressService).findAddressById(idMock);

        int resultContent = result.getResponse().getContentLength();

        assertEquals(resultContent, 0, "Incorrect Response content");
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus(), "Incorrect Response Status");
    }

    @Test
    @DisplayName("DELETE Address by id - should delete address by id")
    void WhenRequestDeleteAddressShouldDeleteAddressTest() throws Exception {
        String idMock = ObjectId.get().toString();

        Address addressMock = buildAddressMock();

        when(addressService.findAddressById(idMock)).thenReturn(addressMock);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", idMock)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(addressService).findAddressById(idMock);

        verify(addressService).deleteAddress(idMock);

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus(), "Incorrect Response Status");
    }

    @Test
    @DisplayName("DELETE Address by id - given id that do not exist - should return not found")
    void WhenRequestDeleteAddressShouldReturnNotFoundTest() throws Exception {
        String idMock = ObjectId.get().toString();

        when(addressService.findAddressById(idMock)).thenReturn(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", idMock)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(addressService).findAddressById(idMock);

        verify(addressService, never()).deleteAddress(idMock);

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus(), "Incorrect Response Status");
    }

    private Address buildAddress() {
        return Address.builder()
                .streetName("Av. Barão de Tefé")
                .number("67")
                .complement("")
                .neighbourhood("Saúde")
                .city("Rio de Janeiro")
                .state("RJ")
                .country("Brazil")
                .zipcode("20220-460")
                .build();
    }

    private Address buildAddressMock() {
        return Address.builder()
                .id(ObjectId.get().toString())
                .streetName("Av. Barão de Tefé")
                .number("67")
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