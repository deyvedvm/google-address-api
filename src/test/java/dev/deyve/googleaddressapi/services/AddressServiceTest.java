package dev.deyve.googleaddressapi.services;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import dev.deyve.googleaddressapi.models.Address;
import dev.deyve.googleaddressapi.repositories.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static dev.deyve.googleaddressapi.utils.TestUtil.buildAddress;
import static dev.deyve.googleaddressapi.utils.TestUtil.buildAddressMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class AddressServiceTest {

    Address addressMock;

    @InjectMocks
    AddressService addressService;

    @Mock
    AddressRepository addressRepository;

    @Mock
    GoogleRestService googleRestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        addressMock = buildAddressMock();
    }

    @Test
    @DisplayName("Get Addresses - return all addresses")
    void shouldFindAllAddressesTest() {
        List<Address> addressListMock = Collections.singletonList(addressMock);

        when(addressRepository.findAll()).thenReturn(addressListMock);

        List<Address> addressList = addressService.findAddresses();

        verify(addressRepository).findAll();

        assertEquals(1, addressListMock.size(), "Result size is different");
        assertEquals(addressList, addressListMock, "Result is different");
    }

    @Test
    @DisplayName("Save Addresses without location - should save address")
    void shouldSaveAddressTest() throws InterruptedException, ApiException, CloneNotSupportedException, IOException {
        Address address = buildAddress();
        LatLng locationMock = new LatLng(-22.8962282, -43.1866427);

        when(googleRestService.findLocation(any(Address.class))).thenReturn(locationMock);

        when(addressRepository.save(any(Address.class))).thenReturn(addressMock);

        Address addressResult = addressService.saveAddress(address);

        verify(addressRepository, times(1)).save(any(Address.class));

        verify(addressRepository).save(any(Address.class));

        assertEquals(addressResult.getId(), addressMock.getId(), "Id is not defined");
        assertEquals(addressResult.getLatitude(), locationMock.lat, "Latitude is not defined");
        assertEquals(addressResult.getLongitude(), locationMock.lng, "Longitude is not defined");
    }
}