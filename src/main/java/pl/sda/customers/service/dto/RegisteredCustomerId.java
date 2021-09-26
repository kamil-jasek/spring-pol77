package pl.sda.customers.service.dto;

import java.util.UUID;
import lombok.NonNull;
import lombok.Value;

@Value
public class RegisteredCustomerId {

    @NonNull
    UUID id;
}
