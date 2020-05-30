package dev.deyve.googleaddressapi.services;

import com.google.maps.errors.ApiException;
import dev.deyve.googleaddressapi.models.Address;
import dev.deyve.googleaddressapi.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Address Service
 */
@Service
@AllArgsConstructor
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;

    private final GoogleRestService googleRestService;

    /**
     * Find All Address
     *
     * @return List of Addresses
     */
    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    /**
     * Save Address
     *
     * @param address - Address to be saved
     * @return Address saved
     */
    public Address saveAddress(Address address) throws InterruptedException, ApiException, IOException, CloneNotSupportedException {
        logger.info("Saving address: {}", address);

        if (Objects.isNull(address.getLatitude()) || Objects.isNull(address.getLongitude())) {
            Address addressWithCoordinates = googleRestService.findLocation(address);

            logger.info("Saving address with google coordinates: {}", addressWithCoordinates);

            return addressRepository.save(addressWithCoordinates);
        } else {
            Address savedAddress = addressRepository.save(address);

            logger.info("Saved address: {}", savedAddress);

            return savedAddress;
        }
    }


    /**
     * Find Address By Id
     *
     * @param id - Id of Address
     * @return Address
     */
    public Address findAddressById(String id) {
        Optional<Address> address = addressRepository.findById(id);

        return address.orElse(null);
    }

    /**
     * Update Address
     *
     * @param address
     * @return
     */
    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    /**
     * Delete Address
     *
     * @param id - Id of Address
     */
    public void deleteAddress(String id) {
        addressRepository.deleteById(id);
    }

    private Boolean validateLatitude(Double latitude) {
        return latitude == null;
    }
}