package pl.sda.customers.service;

import javax.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.customers.entity.Company;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.service.dto.RegisterCompanyForm;
import pl.sda.customers.service.dto.RegisteredCustomerId;
import pl.sda.customers.service.exception.EmailAlreadyExistsException;
import pl.sda.customers.service.exception.VatAlreadyExistsException;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    @NonNull
    private final CustomerRepository repository;

//    public CustomerService(@NonNull CustomerRepository repository) {
//        this.repository = repository;
//    }

    public RegisteredCustomerId registerCompany(@NonNull RegisterCompanyForm form) {
        if (repository.emailExists(form.getEmail())) {
            throw new EmailAlreadyExistsException("email exists: " + form.getEmail());
        }
        if (repository.vatExists(form.getVat())) {
            throw new VatAlreadyExistsException("vat exists: " + form.getVat());
        }
        final var company = new Company(form.getEmail(), form.getName(), form.getVat());
        repository.save(company);
        return new RegisteredCustomerId(company.getId());
    }
}
