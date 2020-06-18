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
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static dev.deyve.googleaddressapi.utils.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@WebMvcTest(controllers = AddressController.class)
class AddressControllerTest {

    private final String URL = "http://localhost:8080/api/address";

    Address addressMock;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        addressMock = buildAddressMock();
    }

    @Test
    @DisplayName("GET Address - should return all addresses")
    void WhenRequestGetAddressesShouldReturnAllAddressTest() throws Exception {
        List<Address> addressListMock = Collections.singletonList(addressMock);

        when(addressService.findAddresses()).thenReturn(addressListMock);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(addressService).findAddresses();

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

        when(addressService.saveAddress(any(Address.class))).thenReturn(addressMock);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .content(objectMapper.writeValueAsString(address))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(addressService).saveAddress(any(Address.class));

        Address addressResult = buildAddressResult(objectMapper, result);

        assertEquals(addressMock, addressResult, "Incorrect Response content");
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus(), "Incorrect Response Status");
    }

    @Test
    @DisplayName("POST Address - try save address with street name null - should throw validation error")
    void WhenRequestPostAddressWithStreetNameNullTest() throws Exception {
        try {
            Address address = buildAddress();
            address.setStreetName(null);

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                    .content(objectMapper.writeValueAsString(address))
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn();

            String expectedMessage = "StreetName not be null";

            String actualMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

            assertTrue(actualMessage.contains(expectedMessage), "Message is different");

            assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus(), "Incorrect Response Status");

        } catch (MethodArgumentNotValidException e) {
            assertThat(e).hasCauseExactlyInstanceOf(MethodArgumentNotValidException.class);
        }
    }

    @Test
    @DisplayName("PUT Address - should save address - return address updated")
    void WhenRequestPutAddressShouldUpdatedAddressTest() throws Exception {
        Address address = buildAddress();
        address.setId(ObjectId.get().toString());

        when(addressService.findAddressById(address.getId())).thenReturn(address);

        when(addressService.updateAddress(any(Address.class))).thenReturn(addressMock);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(URL + "/{id}", address.getId())
                .content(objectMapper.writeValueAsString(address))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(addressService).findAddressById(any(String.class));

        verify(addressService).updateAddress(any(Address.class));

        Address addressResult = buildAddressResult(objectMapper, result);

        assertEquals(addressMock, addressResult, "Incorrect Response content");
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus(), "Incorrect Response Status");
    }

    @Test
    @DisplayName("PUT Address - should try to save address - return not found")
    void WhenRequestPutAddressShouldReturnNotFoundTest() throws Exception {
        Address address = buildAddress();
        address.setId(ObjectId.get().toString());

        when(addressService.findAddressById(address.getId())).thenReturn(null);

        when(addressService.updateAddress(any(Address.class))).thenReturn(any(Address.class));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(URL + "/{id}", address.getId())
                .content(objectMapper.writeValueAsString(address))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(addressService).findAddressById(any(String.class));

        verify(addressService, never()).updateAddress(any(Address.class));

        int contentLength = result.getResponse().getContentLength();

        assertEquals(0, contentLength, "Incorrect Response contentLength");
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus(), "Incorrect Response Status");
    }

    @Test
    @DisplayName("GET Address by id - should return address")
    void WhenRequestGetAddressByIdShouldReturnAddressTest() throws Exception {
        when(addressService.findAddressById(addressMock.getId())).thenReturn(addressMock);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", addressMock.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(addressService).findAddressById(addressMock.getId());

        Address address = buildAddressResult(objectMapper, result);

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
    @DisplayName("DELETE Address by id - should delete address")
    void WhenRequestDeleteAddressShouldDeleteAddressTest() throws Exception {
        String idMock = ObjectId.get().toString();

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
}