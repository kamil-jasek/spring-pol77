package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CustomerTest extends EntityTest {

    @Test
    void shouldSaveCompany() {
        // given
        final var company = new Company("j@wp.pl", "Comp S.A.", "PL920200222");

        // when
        persist(company);

        // then
        final var readCompany = em.find(Company.class, company.getId());
        assertEquals(company, readCompany);
    }
}