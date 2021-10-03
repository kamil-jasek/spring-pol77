package pl.sda.customers.service;

import static com.google.maps.model.AddressComponentType.COUNTRY;
import static com.google.maps.model.AddressComponentType.LOCALITY;
import static com.google.maps.model.AddressComponentType.POSTAL_CODE;
import static com.google.maps.model.AddressComponentType.PREMISE;
import static com.google.maps.model.AddressComponentType.ROUTE;
import static com.google.maps.model.AddressComponentType.STREET_NUMBER;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.LatLng;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.customers.entity.Address;
import pl.sda.customers.service.exception.InvalidCoordinatesException;

@Component
@RequiredArgsConstructor
final class GoogleReverseGeocoding implements ReverseGeocoding {

    @NonNull
    private final GeoApiContext context;

    @Override
    public Address reverse(double latitude, double longitude) {
        try {
            final var results = GeocodingApi
                .reverseGeocode(context, new LatLng(latitude, longitude))
                .await(); // request http do google

            if (results.length == 0) {
                throw new InvalidCoordinatesException(format("invalid lat: %s long: %s", latitude, longitude));
            }

            final var addressComponents = results[0].addressComponents;

            final var streetNumber = findValue(addressComponents, List.of(STREET_NUMBER, PREMISE));
            final var street = findOptionalValue(addressComponents, ROUTE);
            final var city = findValue(addressComponents, LOCALITY);
            final var zipCode = findValue(addressComponents, POSTAL_CODE);
            final var countryCode = findValue(addressComponents, COUNTRY);

            return new Address(
                street != null ? street + " " + streetNumber : streetNumber,
                city,
                zipCode,
                countryCode);
        } catch (ApiException | InterruptedException | IOException ex) {
            throw new ReverseGeocodingException("google is failing", ex);
        }
    }

    private String findValue(AddressComponent[] components, AddressComponentType type) {
        for (final var component : components) {
            if (asList(component.types).contains(type)) {
                return component.shortName;
            }
        }
        throw new InvalidCoordinatesException("cannot find address part: " + type);
    }

    private String findValue(AddressComponent[] components, Collection<AddressComponentType> types) {
        for (final var component : components) {
            // list 1 contains any element from list 2
            if (stream(component.types).anyMatch(types::contains)) {
                return component.shortName;
            }
        }
        throw new InvalidCoordinatesException("cannot find address part: " + types);
    }

    private String findOptionalValue(AddressComponent[] components, AddressComponentType type) {
        for (final var component : components) {
            if (asList(component.types).contains(type)) {
                return component.shortName;
            }
        }
        return null;
    }
}
