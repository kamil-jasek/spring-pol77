package pl.sda.customers.service;

import javax.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.customers.entity.CustomerRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerAddressService {

    @NonNull
    private final CustomerRepository repository;

    @NonNull
    private final ReverseGeocoding reverseGeocoding;

    public CreatedAddress addAddress(AddAddressForm form) {
        if (!repository.existsById(form.getCustomerId())) {
            throw new CustomerNotExistsException("customer not exists: " + form.getCustomerId());
        }
        final var address = reverseGeocoding.reverse(form.getLatitude(), form.getLongitude());
        final var customer = repository.getById(form.getCustomerId());
        customer.addAddress(address);
        repository.save(customer);
        return new CreatedAddress(customer.getId(),
            address.getId(),
            address.getStreet(),
            address.getCity(),
            address.getZipCode(),
            address.getCountryCode());
    }
}
