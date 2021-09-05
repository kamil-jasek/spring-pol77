package pl.sda.customers.entity;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Person> findByLastName(String lastName); // --> select * from customers c where c.last_name = ?

    List<Person> findByFirstNameStartingWithIgnoreCaseAndLastNameStartingWithIgnoreCase(String firstName, String lastName);

    List<Customer> findByEmailIgnoreCaseEndingWith(String email);

    @Query("from Person p where upper(p.firstName) like upper(?1) and upper(p.lastName) like upper(?2)")
    List<Person> searchPeople(String firstName, String lastName);
}
