package pl.sda.customers.entity;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Person> findByLastName(String lastName); // --> select * from customers c where c.customer_type = 'PERSON' and c.last_name = ?

    List<Person> findByFirstNameStartingWithIgnoreCaseAndLastNameStartingWithIgnoreCase(String firstName, String lastName);

    List<Customer> findByEmailIgnoreCaseEndingWith(String email);

    @Query("from Person p where upper(p.firstName) like upper(?1) and upper(p.lastName) like upper(?2)")
    List<Person> searchPeople(String firstName, String lastName);

    // select c.* from customers c
    // inner join addresses a on a.customer_id = c.id
    // where upper(a.city) = upper(?1)
    @Query("from Customer c inner join c.addresses a where upper(a.city) = upper(?1)")
    List<Customer> findCustomersInCity(String city);

    @Query("from Company c inner join c.addresses a where upper(a.countryCode) = upper(:countryCode) order by c.name asc")
    List<Company> findCompaniesInCountry(@Param("countryCode") String code);

    @Query("select p.addresses from Person p where upper(p.lastName) = upper(?1)")
    List<Address> findAllAddressesForLastName(String lastName);

    @Query("select (count(c) > 0) from Customer c where upper(c.email) = upper(?1)")
    boolean emailExists(String email);

    @Query("select (count(c) > 0) from Company c where c.vat = ?1")
    boolean vatExists(String vat);

    @Query("select (count(p) > 0) from Person p where p.pesel = ?1")
    boolean peselExists(String pesel);

    @Query("select a.city, count(c) from Customer c inner join c.addresses a group by a.city order by a.city asc")
    List<Object[]> countCustomersByCity();
    // Kraków     |  3   =  row[0]  --->  Object[0]  = Kraków, Object[1] = 3L
    // Warszawa   |  2   =  row[1]  --->  Object[0]  = Warszawa, Object[1] = 2L

    interface CountCustomerByCountryCode {  // Rozwiązanie ze SPRING DATA
        String getCountryCode();
        int getCount();
    }

    @Query("select a.countryCode as countryCode, count(c) as count from Customer c "
        + "inner join c.addresses a "
        + "group by a.countryCode "
        + "order by a.countryCode asc")
    List<CountCustomerByCountryCode> countCustomersByCountryCode();

    @Query("select new pl.sda.customers.entity.CompanyZipCodeView(c.name, c.vat, a.zipCode) "
        + "from Company c inner join c.addresses a where a.zipCode like ?1")
    List<CompanyZipCodeView> findCompaniesWithZipCode(String zipCode);

    @Query("from PersonView v where upper(v.email) like upper(?1)")
    List<PersonView> findPersonViewByEmail(String email);

    @Modifying
    @Query("update Address set countryCode = :countryCode where city = :city")
    int updateCountryCodeForCity(String city, String countryCode);

    @Query("select count(a) from Address a where a.city = :city and a.countryCode = :countryCode")
    int countCityWithCountryCode(String city, String countryCode);

    @Query("from Address a where a.city = :city")
    List<Address> findByCity(String city);

    @Modifying
    @Query("delete from Address where zipCode = :zipCode")
    int deleteAddressesWithZipCode(String zipCode);

    @Query("select count(a) from Address a where a.zipCode = ?1")
    int countAddressesWithZipCode(String zipCode);
}
