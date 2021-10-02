package pl.sda.customers.service;

import pl.sda.customers.entity.Address;

interface ReverseGeocoding {

    class ReverseGeocodingException extends RuntimeException {

        public ReverseGeocodingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    Address reverse(double latitude, double longitude);
}
