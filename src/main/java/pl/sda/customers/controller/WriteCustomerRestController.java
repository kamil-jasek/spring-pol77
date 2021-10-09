package pl.sda.customers.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.customers.service.CustomerRegistrationService;
import pl.sda.customers.service.dto.RegisterCompanyForm;
import pl.sda.customers.service.dto.RegisterPersonForm;
import pl.sda.customers.service.dto.RegisteredCustomerId;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
final class WriteCustomerRestController {

    @NonNull
    private final CustomerRegistrationService service;

    @PostMapping("/companies") // POST -> /api/companies
    // JSON -> { "name": "Comp S.A.", ..... } -> DTO (RegisterCompanyForm)
    ResponseEntity<RegisteredCustomerId> registerCompany(@RequestBody RegisterCompanyForm form) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(service.registerCompany(form));
    }

    @PostMapping("/people") // POST -> /api/people
    // JSON -> { "firstName": "Jan", ..... } -> DTO (RegisterPersonForm)
    ResponseEntity<RegisteredCustomerId> registerPerson(@RequestBody RegisterPersonForm form) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(service.registerPerson(form));
    }

    // TODO - add address to customer
    // POST -> /api/customer/{id}/addresses
    // latitude + longitude
}
