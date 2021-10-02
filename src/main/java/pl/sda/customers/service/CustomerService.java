package pl.sda.customers.service;

import javax.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.customers.entity.Company;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.entity.Person;
import pl.sda.customers.service.dto.RegisterCompanyForm;
import pl.sda.customers.service.dto.RegisterPersonForm;
import pl.sda.customers.service.dto.RegisteredCustomerId;
import pl.sda.customers.service.exception.EmailAlreadyExistsException;
import pl.sda.customers.service.exception.PeselAlreadyExistsException;
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
        final var company = Company.createWith(form);
        repository.save(company);
        return new RegisteredCustomerId(company.getId());
    }

    public RegisteredCustomerId registerPerson(@NonNull RegisterPersonForm form) {
        if (repository.emailExists(form.getEmail())) {
            throw new EmailAlreadyExistsException("email exists: " + form.getEmail());
        }
        if (repository.peselExists(form.getPesel())) {
            throw new PeselAlreadyExistsException("pesel exists: " + form.getPesel());
        }
        final var person = Person.createWith(form);
        repository.save(person);
        return new RegisteredCustomerId(person.getId());
    }
}
