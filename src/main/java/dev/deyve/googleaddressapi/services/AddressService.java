package dev.deyve.googleaddressapi.services;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
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
    public List<Address> findAddresses() {
        logger.info("Find Addresses...");

        List<Address> addresses = addressRepository.findAll();

        logger.debug("Addresses: {}", addresses);

        return addresses;
    }

    /**
     * Save Address
     *
     * @param address - Address to be saved
     * @return Address saved
     */
    public Address saveAddress(Address address) throws InterruptedException, ApiException, IOException, CloneNotSupportedException {
        logger.info("Saving address...");

        if (Objects.isNull(address.getLatitude()) || Objects.isNull(address.getLongitude())) {
            LatLng location = googleRestService.findLocation(address);
            address.setLatitude(location.lat);
            address.setLongitude(location.lng);

            logger.debug("Saving address with google coordinates: {}", address);

            return addressRepository.save(address);
        } else {
            Address savedAddress = addressRepository.save(address);

            logger.debug("Saved address: {}", savedAddress);

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
        logger.info("Finding address by id...");

        Optional<Address> address = addressRepository.findById(id);

        logger.debug("Address found: {}", address);

        return address.orElse(null);
    }

    /**
     * Update Address
     *
     * @param address - Address to be saved
     * @return Updated Address
     */
    public Address updateAddress(Address address) {
        logger.info("Updating address...");

        Address updatedAddress = addressRepository.save(address);

        logger.debug("Updated address: {}", updatedAddress);

        return updatedAddress;
    }

    /**
     * Delete Address
     *
     * @param id - Id of Address
     */
    public void deleteAddress(String id) {
        logger.info("Deleting address by id...");

        addressRepository.deleteById(id);
    }
}
