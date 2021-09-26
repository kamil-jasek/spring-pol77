package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private EntityManager em;

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
        final var company1 = new Company("com@wp.pl", "Januszex", "1234567");
        final var company2 = new Company("squat@wp.pl", "Poltex", "2389232");
        final var company3 = new Company("port@wp.pl", "TylkoPolska", "3459898");
        final var company4 = new Company("gryka@wp.pl", "GrykPol", "9871293471");

        company1.addAddress(new Address("str", "Dzierżoniów", "23-234","PL"));
        company2.addAddress(new Address("str", "Zgorzelec", "32-654","PL"));
        company3.addAddress(new Address("str", "Dzierżoniów", "19-098","DE"));
        company4.addAddress(new Address("str", "Dzierżoniów", "91-234","PL"));

        repository.saveAllAndFlush(List.of(company1, company2, company3, company4));

        // when - dodaj metodę w repository, która szuka firm w danym kraju np. PL, a rezultaty są posortowane po nazwie firmy
         final var result = repository.findCompaniesInCountry("pl");

        // then - sprawdź czy wyniki się zgadzają z założeniami
        assertEquals(List.of(company4, company1, company2), result);
    }

    @Test
    void shouldFindAllAddressesForLastName() {
        // given - utwórz kilka osób wraz z adresami
        final var customer1 = new Person("ak@wp.pl", "Jan", "Nowak", "92929929929");
        final var customer2 = new Person("qw@wp.pl", "Jan", "Kowalski", "83838288233");
        final var customer3 = new Person("cx@wp.pl", "Janeczek", "Nowaczkiewicz", "83838288233");
        final var customer4 = new Person("er@on.pl", "Mateusz", "Kowalski", "93939939424");

        final var address1 = new Address("str", "Kraków", "33-220", "PL");
        final var address2 = new Address("str", "Wawa", "44-300", "PL");

        customer1.addAddress(new Address("str", "Wawa", "04-333", "PL"));
        customer2.addAddress(address1);
        customer2.addAddress(address2);
        customer3.addAddress(new Address("str", "Wrocław", "55-200", "PL"));
        final var address3 = new Address("str2", "Kraków", "33-220", "PL");
        customer4.addAddress(address3);

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));

        // when - dodaj metodę w repository, która zwróci wszsytkie adresy pod którymi mieszkają osoby o nazwisku: "Kowalski"
         final var result = repository.findAllAddressesForLastName("Kowalski");

        // then - sprawdź czy wyniki się zgadzają z założeniami
        assertTrue(List.of(address1, address2, address3).containsAll(result));
    }

    @Test
    void shouldCountCustomersByCity() {
        // given - utwórz różnych klientów wraz z adresami
        final var customer1 = new Person("ak@wp.pl", "Jan", "Nowak", "92929929929");
        final var customer2 = new Person("qw@wp.pl", "Jan", "Kowalski", "83838288233");
        final var customer3 = new Person("cx@wp.pl", "Janeczek", "Nowaczkiewicz", "83838288233");
        final var customer4 = new Person("er@on.pl", "Mateusz", "Kowalski", "93939939424");

        customer1.addAddress(new Address("str", "Warszawa", "04-333", "PL"));
        customer2.addAddress(new Address("str", "Kraków", "33-220", "PL"));
        customer2.addAddress(new Address("str", "Warszawa", "44-300", "PL"));
        customer3.addAddress(new Address("str", "Kraków", "55-200", "PL"));
        customer4.addAddress(new Address("str2", "Kraków", "33-220", "PL"));

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));

        // when - napisz query, które zwróci miast + liczbę klientów w danym mieście np.
        // city     |  number_of_customers
        // Warszawa |  2
        // Kraków   |  3
         final var result = repository.countCustomersByCity();

        // then - sprawdź czy wyniki się zgadzają z założeniami
        assertEquals(2, result.size());
        assertArrayEquals(new Object[] { "Kraków", 3L }, result.get(0));
        assertArrayEquals(new Object[] { "Warszawa", 2L }, result.get(1));
    }

    @Test
    void shouldCountCustomersInCountry() {
        // given - utwórz różnych klientów wraz z adresami
        final var customer1 = new Person("ak@wp.pl", "Jan", "Nowak", "92929929929");
        final var customer2 = new Person("qw@wp.pl", "Jan", "Kowalski", "83838288233");
        final var customer3 = new Person("cx@wp.pl", "Janeczek", "Nowaczkiewicz", "83838288233");
        final var customer4 = new Person("er@on.pl", "Mateusz", "Kowalski", "93939939424");

        customer1.addAddress(new Address("str", "Berlin", "04-333", "DE"));
        customer2.addAddress(new Address("str", "Kraków", "33-220", "PL"));
        customer2.addAddress(new Address("str", "Berlin", "44-300", "DE"));
        customer3.addAddress(new Address("str", "Kraków", "55-200", "PL"));
        customer4.addAddress(new Address("str2", "Kraków", "33-220", "PL"));

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));

        // when
        final var result = repository.countCustomersByCountryCode();

        // then
        assertEquals(2, result.size());
        final var row1 = result.get(0);
        assertEquals("DE", row1.getCountryCode());
        assertEquals(2, row1.getCount());
        final var row2 = result.get(1);
        assertEquals("PL", row2.getCountryCode());
        assertEquals(3, row2.getCount());
    }

    @Test
    void shouldFindCompaniesWithZipCode() {
        // given
        final var company1 = new Company("com@wp.pl", "Januszex", "1234567");
        final var company2 = new Company("squat@wp.pl", "Poltex", "2389232");
        final var company3 = new Company("port@wp.pl", "TylkoPolska", "3459898");
        final var company4 = new Company("gryka@wp.pl", "GrykPol", "9871293471");

        company1.addAddress(new Address("str", "Dzierżoniów", "23-999","PL"));
        company2.addAddress(new Address("str", "Zgorzelec", "32-654","PL"));
        company3.addAddress(new Address("str", "Dzierżoniów", "23-098","DE"));
        company4.addAddress(new Address("str", "Dzierżoniów", "23-234","PL"));

        repository.saveAllAndFlush(List.of(company1, company2, company3, company4));

        // when
        final var result = repository.findCompaniesWithZipCode("23%");

        // then
        assertTrue(List.of(
            new CompanyZipCodeView("Januszex", "1234567", "23-999"),
            new CompanyZipCodeView("TylkoPolska", "3459898", "23-098"),
            new CompanyZipCodeView("GrykPol", "9871293471", "23-234"))
            .containsAll(result));
    }

    @Test
    void shouldFindPersonViewByEmail() {
        // given
        final var customer1 = new Person("ak@wp.pl", "Jan", "Nowak", "92929929929");
        final var customer2 = new Person("qw@wp.com", "Jan", "Kowalski", "83838288233");
        final var customer3 = new Person("akc@wp.pl", "Janeczek", "Nowaczkiewicz", "83838288233");
        final var customer4 = new Person("er@on.com", "Mateusz", "Kowalski", "93939939424");

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));

        // when
        final var result = repository.findPersonViewByEmail("%.pl");

        // then
        assertTrue(List.of(
            new PersonView(customer1.getId(), customer1.getEmail(), customer1.getPesel()),
            new PersonView(customer3.getId(), customer3.getEmail(), customer3.getPesel()))
            .containsAll(result));
    }

    @Test
    void shouldUpdateCountryCodeForCity() {
        // given
        final var company1 = new Company("com@wp.pl", "Januszex", "1234567");
        final var company2 = new Company("squat@wp.pl", "Poltex", "2389232");
        final var company3 = new Company("port@wp.pl", "TylkoPolska", "3459898");
        final var company4 = new Company("gryka@wp.pl", "GrykPol", "9871293471");

        company1.addAddress(new Address("str", "Dzierżoniów", "23-999","PL"));
        company2.addAddress(new Address("str", "Zgorzelec", "32-654","PL"));
        company3.addAddress(new Address("str", "Dzierżoniów", "23-098","DE"));
        company4.addAddress(new Address("str", "Dzierżoniów", "23-234","DE"));

        repository.saveAllAndFlush(List.of(company1, company2, company3, company4));

        // when
        final int result = repository.updateCountryCodeForCity("Dzierżoniów", "PL");
        em.clear(); // clear cache

        // then
        assertEquals(3, result);
        assertEquals(0, repository.countCityWithCountryCode("Dzierżoniów", "DE"));
        final var addresses = repository.findByCity("Dzierżoniów");
        assertEquals(3, addresses.size());
        addresses.forEach(address -> assertEquals("PL", address.getCountryCode()));
    }

    @Test
    void shouldDeleteAllAddressesWithZipCode() {
        // given - przygotowanie danych testowych
        final var company1 = new Company("com@wp.pl", "Januszex", "1234567");
        final var company2 = new Company("squat@wp.pl", "Poltex", "2389232");
        final var company3 = new Company("port@wp.pl", "TylkoPolska", "3459898");
        final var company4 = new Company("gryka@wp.pl", "GrykPol", "9871293471");

        company1.addAddress(new Address("str", "Dzierżoniów", "23-999","PL"));
        company2.addAddress(new Address("str", "Zgorzelec", "32-654","PL"));
        company3.addAddress(new Address("str", "Dzierżoniów", "23-098","DE"));
        company4.addAddress(new Address("str", "Dzierżoniów", "23-999","DE"));

        repository.saveAllAndFlush(List.of(company1, company2, company3, company4));

        // when
        final int result = repository.deleteAddressesWithZipCode("23-999");

        // then
        assertEquals(2, result);
        assertEquals(0, repository.countAddressesWithZipCode("23-999"));
    }
}