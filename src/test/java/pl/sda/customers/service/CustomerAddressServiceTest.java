package pl.sda.customers.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.sda.customers.entity.Address;
import pl.sda.customers.entity.Company;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.service.dto.AddAddressForm;
import pl.sda.customers.service.dto.CreatedAddress;

@SpringBootTest
@Transactional
class CustomerAddressServiceTest {

    @Autowired
    private CustomerAddressService service;

    @Autowired
    private CustomerRepository repository;

    @MockBean
    private ReverseGeocoding reverseGeocoding;

    @Test
    void shouldAddAddressToCustomer() {
        // given
        final var customer = new Company("abc@wp.pl", "Xyz S.A.", "PL83893933");
        repository.save(customer);
        final var address = new Address("str", "Wawa", "01-200", "PL");
        when(reverseGeocoding.reverse(anyDouble(), anyDouble())).thenReturn(address);
        final var form = new AddAddressForm(customer.getId(), 52.242799, 20.979061);

        // when
        final var createdAddress = service.addAddress(form);

        // then
        assertEquals(new CreatedAddress(customer.getId(),
            address.getId(),
            "str",
            "Wawa",
            "01-200",
            "PL"), createdAddress);
    }
}