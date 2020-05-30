package dev.deyve.googleaddressapi.errors;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException(String id) {
        super(String.format("Address with id %s not found", id));
    }
}
