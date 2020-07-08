package dev.deyve.googleaddressapi.controllers;

import com.google.maps.errors.ApiException;
import dev.deyve.googleaddressapi.models.Address;
import dev.deyve.googleaddressapi.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "GET Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address created",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Address.class))))})
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
    @Operation(summary = "POST Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Address.class))})})
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
    @Operation(summary = "PUT Address By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Address.class))}),
            @ApiResponse(responseCode = "404", description = "Address not found", content = @Content)})
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
    @Operation(summary = "GET Address By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Address.class))}),
            @ApiResponse(responseCode = "404", description = "Address not found", content = @Content)})
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
    @Operation(summary = "DELETE Address By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Address not found")})
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
