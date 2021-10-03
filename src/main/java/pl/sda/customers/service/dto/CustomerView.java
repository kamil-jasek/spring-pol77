package pl.sda.customers.service.dto;

import java.util.UUID;
import lombok.Value;
import pl.sda.customers.entity.CustomerType;

@Value
public class CustomerView {

    UUID customerId;
    String name;
    String email;
    CustomerType type;
}
