package pl.sda.customers.controller;

import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.customers.service.CustomerAddressService;
import pl.sda.customers.service.CustomerRegistrationService;
import pl.sda.customers.service.dto.AddAddressForm;
import pl.sda.customers.service.dto.CreatedAddress;
import pl.sda.customers.service.dto.RegisterCompanyForm;
import pl.sda.customers.service.dto.RegisterPersonForm;
import pl.sda.customers.service.dto.RegisteredCustomerId;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
final class WriteCustomerRestController {

    @NonNull
    private final CustomerRegistrationService registrationService;

    @NonNull
    private final CustomerAddressService addressService;

    @PostMapping("/companies")
        // POST -> /api/companies
        // JSON -> { "name": "Comp S.A.", ..... } -> DTO (RegisterCompanyForm)
    ResponseEntity<RegisteredCustomerId> registerCompany(@RequestBody RegisterCompanyForm form) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(registrationService.registerCompany(form));
    }

    @PostMapping("/people") // POST -> /api/people
    // JSON -> { "firstName": "Jan", ..... } -> DTO (RegisterPersonForm)
    ResponseEntity<RegisteredCustomerId> registerPerson(@RequestBody RegisterPersonForm form) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(registrationService.registerPerson(form));
    }

    @Value
    static class LatLong {
        double latitude;
        double longitude;
    }

    @PostMapping("/customers/{customerId}/addresses") // POST -> /api/customers/{id}/addresses
    // JSON -> { "latitude": 92993, "longitude": 93993 }
    ResponseEntity<CreatedAddress> addAddress(@PathVariable UUID customerId, @RequestBody LatLong latLong) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(addressService.addAddress(new AddAddressForm(customerId,
                latLong.getLatitude(),
                latLong.getLongitude())));
    }
}
