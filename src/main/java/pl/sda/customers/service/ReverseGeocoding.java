package pl.sda.customers.service;

import pl.sda.customers.entity.Address;

public interface ReverseGeocoding {

    Address reverse(double latitude, double longitude);
}
