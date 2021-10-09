package pl.sda.customers.service.dto;

import java.util.UUID;
import lombok.Value;

@Value
public class AddressView {
    UUID id;
    String street;
    String city;
    String zipCode;
    String countryCode;
}
