package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AddressTest {

    @Autowired
    private EntityManager em;

    @Test
    @Transactional
    void shouldSaveAddress() {
        // given
        final var address = new Address("str", "Wawa", "01-200", "PL");

        // when - save to db
        // KLUCZ: ID, Wartość: Encja
        em.persist(address); // dodanie do cache
        em.flush(); // wysłanie cache do db: insert into addresses ..........
        em.clear(); // czyszczenie cache

        // then
        final var readAddress = em.find(Address.class, address.getId()); // select a.* from addresses
        assertEquals(address, readAddress);
    }
}