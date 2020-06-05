package dev.deyve.googleaddressapi.controllers;

import com.google.maps.errors.ApiException;
import dev.deyve.googleaddressapi.models.Address;
import dev.deyve.googleaddressapi.services.AddressService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Address Controller
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/address")
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    private final AddressService addressService;

    /**
     * Get Addresses
     *
     * @return List of Addresses
     */
    @GetMapping
    public ResponseEntity<List<Address>> getAddresses() {
        List<Address> addressList = addressService.findAddresses();

        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    /**
     * Post Address
     *
     * @param address - Address to be saved
     * @return Address saved
     */
    @PostMapping
    public ResponseEntity<Address> postAddress(@Valid @RequestBody Address address) throws InterruptedException, ApiException, IOException, CloneNotSupportedException {

        Address addressSaved = addressService.saveAddress(address);

        logger.info("Address saved: {}", addressSaved);

        return new ResponseEntity<>(addressSaved, HttpStatus.CREATED);
    }

    /**
     * Put Address
     *
     * @param id      - Id of Address
     * @param address - Address to be updated
     * @return Address updated
     */
    @PutMapping("/{id}")
    public ResponseEntity<Address> putAddress(@PathVariable String id, @RequestBody Address address) {
        Address addressById = addressService.findAddressById(id);

        if (addressById == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Address addressUpdated = addressService.updateAddress(address);

        return new ResponseEntity<>(addressUpdated, HttpStatus.OK);
    }

    /**
     * Get Address By Id
     *
     * @param id - Id of Address
     * @return Address
     */
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable String id) {
        Address address = addressService.findAddressById(id);

        if (address == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    /**
     * Delete Address
     *
     * @param id - Id of Address to be deleted
     * @return void
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable String id) {
        Address address = addressService.findAddressById(id);

        if (address == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        addressService.deleteAddress(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
