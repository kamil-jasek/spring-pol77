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

    @Test
    void shouldFindCustomerByEmail() {
        // given
        final var customer1 = new Person("ak@op.pl", "Jan", "Nowak", "92929929929");
        final var customer2 = new Person("qw@WP.pl", "Jan", "Kowalski", "83838288233");
        final var customer3 = new Person("cx@wp.pl", "Janeczek", "Nowaczkiewicz", "83838288233");
        repository.saveAllAndFlush(List.of(customer1, customer2, customer3));

        // when
        final var result = repository.findByEmailIgnoreCaseEndingWith("wp.pl");

        // then
        assertEquals(List.of(customer2, customer3), result);
    }

    @Test
    void shouldSearchPeople() {
        // given
        final var customer1 = new Person("ak@wp.pl", "Jan", "Nowak", "92929929929");
        final var customer2 = new Person("qw@wp.pl", "Jan", "Kowalski", "83838288233");
        final var customer3 = new Person("cx@wp.pl", "Janeczek", "Nowaczkiewicz", "83838288233");
        repository.saveAllAndFlush(List.of(customer1, customer2, customer3));

        // when
        final var result = repository.searchPeople("jan%", "nowa%");

        // then
        assertEquals(List.of(customer1, customer3), result);
    }

    @Test
    void shouldFindCustomersInCity() {
        // given
        final var customer1 = new Person("ak@wp.pl", "Jan", "Nowak", "92929929929");
        final var customer2 = new Person("qw@wp.pl", "Jan", "Kowalski", "83838288233");
        final var customer3 = new Person("cx@wp.pl", "Janeczek", "Nowaczkiewicz", "83838288233");
        final var customer4 = new Company("er@on.pl", "Comp S.A.", "9393993944");

        customer1.addAddress(new Address("str", "Wawa", "04-333", "PL"));
        customer2.addAddress(new Address("str", "Kraków", "33-220", "PL"));
        customer2.addAddress(new Address("str", "Wawa", "44-300", "PL"));
        customer3.addAddress(new Address("str", "Wrocław", "55-200", "PL"));
        customer4.addAddress(new Address("str2", "Kraków", "33-220", "PL"));

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));

        // when
        final List<Customer> result = repository.findCustomersInCity("kraków");
        // select c.* from customers c
        // inner join addresses a on a.customer_id = c.id
        // where upper(a.city) = upper(?1)

        // then
        assertTrue(List.of(customer2, customer4).containsAll(result));
    }

    @Test
    void shouldFindCompaniesInCountrySortedByName() {
        // given - utwórz kilka firm wraz z adresami i zapisz poprzez repository

        // when - dodaj metodę w repository, która szuka firm w danym kraju np. PL, a rezultaty są posortowane po nazwie firmy
        // np.
        // final var result = repository.findCompaniesInCountry("PL");

        // then - sprawdź czy wyniki się zgadzają z założeniami
        fail(); // usuń tą linię jak skończysz test
    }

    @Test
    void shouldFindAllAddressesForLastName() {
        // given - utwórz kilka osób wraz z adresami

        // when - dodaj metodę w repository, która zwróci wszsytkie adresy pod którymi mieszkają osoby o nazwisku: "Kowalski"
        // np.
        // final var result = repository.findAllAddressesForLastName("Kowalski");

        // then - sprawdź czy wyniki się zgadzają z założeniami
        fail(); // usuń tą linię jak skończysz test
    }

    @Test
    void shouldCountCustomersByCity() {
        // given - utwórz różnych klientów wraz z adresami

        // when - napisz query, które zwróci miast + liczbę klientów w danym mieście np.
        // city     |  number_of_customers
        // Warszawa |  2
        // Kraków   |  3
        // final var result = repository.countCustomersByCity();

        // then - sprawdź czy wyniki się zgadzają z założeniami
        fail(); // usuń tą linię jak skończysz test
    }
}