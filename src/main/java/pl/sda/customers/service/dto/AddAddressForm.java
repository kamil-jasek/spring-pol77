package pl.sda.customers.service.dto;

import java.util.UUID;
import lombok.NonNull;
import lombok.Value;

@Value
public class AddAddressForm {

    @NonNull
    UUID customerId;
    double latitude;
    double longitude;
}
