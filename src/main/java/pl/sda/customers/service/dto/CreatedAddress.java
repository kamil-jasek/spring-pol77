package pl.sda.customers.service.dto;

import java.util.UUID;
import lombok.Value;

@Value
public class CreatedAddress {

    UUID customerId;
    UUID addressId;
    String street;
    String city;
    String zipCode;
    String countryCode;
}
