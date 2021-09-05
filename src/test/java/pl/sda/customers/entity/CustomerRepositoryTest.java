package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void shouldSave() {
        // given
        final var customer1 = new Person("ak@wp.pl", "Jan", "Nowak", "92929929929");
        final var customer2 = new Company("wp@wp.pl", "Comp S.A.", "3903003003");

        // when
        repository.saveAllAndFlush(List.of(customer1, customer2));

        // then
        assertEquals(2, repository.count());
    }

    @Test
    void shouldFindPersonByLastname() {
        // given
        final var customer1 = new Person("ak@wp.pl", "Jan", "Nowak", "92929929929");
        final var customer2 = new Person("qw@wp.pl", "Jan", "Kowalski", "83838288233");
        repository.saveAllAndFlush(List.of(customer1, customer2));

        // when
        final var result = repository.findByLastName("Nowak");

        // then
        assertEquals(List.of(customer1), result);
    }

    @Test
    void shouldFindPersonByFirstNameAndLastName() {
        // given
        final var customer1 = new Person("ak@wp.pl", "Jan", "Nowak", "92929929929");
        final var customer2 = new Person("qw@wp.pl", "Jan", "Kowalski", "83838288233");
        final var customer3 = new Person("cx@wp.pl", "Janeczek", "Nowaczkiewicz", "83838288233");
        repository.saveAllAndFlush(List.of(customer1, customer2, customer3));

        // when
        final var result = repository
            .findByFirstNameStartingWithIgnoreCaseAndLastNameStartingWithIgnoreCase("jan", "nowa");

        // then
        assertEquals(List.of(customer1, customer3), result);
    }
}