package pl.sda.customers.entity;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
abstract class EntityTest {

    @Autowired
    protected EntityManager em;

    protected void persist(Object entity) {
        // KLUCZ: ID, Wartość: Encja
        em.persist(entity); // dodanie do cache
        em.flush(); // wysłanie cache do db: insert into addresses ..........
        em.clear(); // czyszczenie cache
    }
}
