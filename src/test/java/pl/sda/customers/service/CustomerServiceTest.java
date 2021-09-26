package pl.sda.customers.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.service.dto.RegisterCompanyForm;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService service;

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
}