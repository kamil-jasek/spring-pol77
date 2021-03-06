package pl.sda.customers.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.customers.entity.Company;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.entity.Person;
import pl.sda.customers.service.dto.RegisterCompanyForm;
import pl.sda.customers.service.dto.RegisterPersonForm;
import pl.sda.customers.service.exception.EmailAlreadyExistsException;
import pl.sda.customers.service.exception.PeselAlreadyExistsException;
import pl.sda.customers.service.exception.VatAlreadyExistsException;

@SpringBootTest
@Transactional
class CustomerRegistrationServiceTest {

    @Autowired
    private CustomerRegistrationService service;

    @Autowired
    private CustomerRepository repository;

    @Test
    void shouldRegisterCompany() {
        // given
        final var form = new RegisterCompanyForm("Comp S.A.", "PL99399393", "abc@wp.pl");

        // when
        final var customerId = service.registerCompany(form);

        // then
        assertNotNull(customerId);
        assertTrue(repository.existsById(customerId.getId()));
    }

    @Test
    void shouldNotRegisterCompanyIfEmailExists() {
        // given
        repository.saveAndFlush(new Company("abc@wp.pl", "Test", "PL939399393"));
        final var form = new RegisterCompanyForm("Compa S.A.", "PL93939333", "abc@wp.pl");

        // when & then
        assertThrows(EmailAlreadyExistsException.class, () -> service.registerCompany(form));
    }

    @Test
    void shouldNotRegisterCompanyIfVatExists() {
        // given
        repository.saveAndFlush(new Company("abc@wp.pl", "Test", "PL939399393"));
        final var form = new RegisterCompanyForm("Compa S.A.", "PL939399393", "xyz@wp.pl");

        // when & then
        assertThrows(VatAlreadyExistsException.class, () -> service.registerCompany(form));
    }

    @Test
    void shouldRegisterPerson() {
        // given
        final var form = new RegisterPersonForm("abc@wp.pl", "Jan", "Kowalski", "98293020202");

        // when
        final var customerId = service.registerPerson(form);

        // then
        assertNotNull(customerId);
        assertTrue(repository.existsById(customerId.getId()));
    }

    @Test
    void shouldNotRegisterPersonIfEmailExists() {
        // given
        repository.saveAndFlush(new Person("abc@wp.pl", "Jan", "Kowalski", "9383929092"));
        final var form = new RegisterPersonForm("abc@wp.pl", "Adam", "Nowak", "82992020303");

        // when & then
        assertThrows(EmailAlreadyExistsException.class, () -> service.registerPerson(form));
    }

    @Test
    void shouldNotRegisterPersonIfPeselExists() {
        // given
        repository.saveAndFlush(new Person("abc@wp.pl", "Jan", "Kowalski", "9383929092"));
        final var form = new RegisterPersonForm("asd@wp.pl", "Adam", "Nowak", "9383929092");

        // when & then
        assertThrows(PeselAlreadyExistsException.class, () -> service.registerPerson(form));
    }
}