package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AddressTest extends EntityTest {

    @Test
    void shouldSaveAddress() {
        // given
        final var address = new Address("str", "Wawa", "01-200", "PL");

        // when - save to db
        persist(address);

        // then
        final var readAddress = em.find(Address.class, address.getId()); // select a.* from addresses a where a.id = ?1
        assertEquals(address, readAddress);
    }
}