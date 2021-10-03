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
import pl.sda.customers.service.dto.RegisteredCustomerId;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
final class WriteCustomerRestController {

    @NonNull
    private final CustomerRegistrationService service;

    @PostMapping("/companies") // POST -> /api/companies
    ResponseEntity<RegisteredCustomerId> registerCompany(@RequestBody RegisterCompanyForm form) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(service.registerCompany(form));
    }

    // TODO - add method for registering person
    // POST -> /api/people
}
