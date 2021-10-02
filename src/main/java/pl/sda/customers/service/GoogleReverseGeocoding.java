package pl.sda.customers.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.LatLng;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.customers.entity.Address;

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
                .await();
        } catch (Exception ex) {
            throw new ReverseGeocodingException("google is failing", ex);
        }
        return null;
    }
}
