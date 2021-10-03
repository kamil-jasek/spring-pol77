package pl.sda.customers.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.service.dto.CustomerView;

@Component
@RequiredArgsConstructor
public class CustomerQuery {

    @NonNull
    private final CustomerRepository repository;

    public List<CustomerView> listCustomers() {
        return repository.findAll().stream()
            .map(customer -> new CustomerView(customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getCustomerType()))
            .collect(toList());
    }
}
