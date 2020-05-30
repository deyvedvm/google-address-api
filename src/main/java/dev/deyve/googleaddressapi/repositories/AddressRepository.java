package dev.deyve.googleaddressapi.repositories;

import dev.deyve.googleaddressapi.models.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Address Repository
 */
public interface AddressRepository extends MongoRepository<Address, String> {

}
