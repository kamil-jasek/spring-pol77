package pl.sda.customers.controller;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.customers.service.CustomerQuery;
import pl.sda.customers.service.dto.CustomerView;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
final class ReadCustomerRestController {

    @NonNull
    private final CustomerQuery query;

    @GetMapping // GET -> /api/customers
    List<CustomerView> getCustomers() {
        return query.listCustomers();
    }

    // TODO - Get single customer details
    // /api/customers/{id}
}
