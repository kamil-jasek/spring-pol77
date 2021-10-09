package pl.sda.customers.controller;

import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.customers.service.CustomerQuery;
import pl.sda.customers.service.dto.CustomerDetails;
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

    // /api/customers/{id} -> JSON { type: "PERSON", "firstName": "Jan", ..., "email": "aca@wp.pl", addresses: []}
    //                        JSON { types: "COMPANY", "name": "ASSA", "vat": "PL33...", ... , "email": "...", addresses: [] }
    @GetMapping("/{customerId}")
    CustomerDetails getCustomerDetails(@PathVariable UUID customerId) {
        return query.getById(customerId);
    }
}
