package dev.deyve.googleaddressapi.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import dev.deyve.googleaddressapi.models.Address;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class GoogleRestService {

    private static final Logger logger = LoggerFactory.getLogger(GoogleRestService.class);

    private final GeoApiContext context;

    public Address findLocation(Address address) throws InterruptedException, ApiException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        StringBuilder addressBuilder = new StringBuilder();
        addressBuilder.append(address.getNumber());
        addressBuilder.append(" ");
        addressBuilder.append(address.getStreetName());
        addressBuilder.append(" ");
        addressBuilder.append(address.getCity());
        addressBuilder.append(", ");
        addressBuilder.append(address.getState());
        addressBuilder.append(" ");
        addressBuilder.append(address.getZipcode());

        System.out.println(addressBuilder);

        Address addressWithLocation = gson.fromJson(gson.toJson(address), Address.class);

        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, addressBuilder.toString()).await();

            logger.info("Location: {}", gson.toJson(results[0].geometry.location));

            LatLng location = results[0].geometry.location;
            addressWithLocation.setLatitude(location.lat);
            addressWithLocation.setLongitude(location.lng);

        } catch (InterruptedException ie) {
            logger.error("InterruptedException: {}", ie.getMessage());
        } catch (ApiException ae) {
            logger.error("ApiException: {}", ae.getMessage());
        } catch (IOException ioe) {
            logger.error("IOException: {}", ioe.getMessage());
        }

        return addressWithLocation;
    }
}
