package pl.sda.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerRegistration {

//    @Autowired -- not recommended
    private final CustomerRepository repository;

//    @Autowired -- not needed
    public CustomerRegistration(CustomerRepository repository) {
        this.repository = repository;
    }

    public void register(String email, String name) {
        System.out.println("registering customer: " + email);
        repository.save(email, name);
        System.out.println("registered customer: " + email);
    }

//    @Autowired -- not recommended
    public void setRepository(CustomerRepository repository) {
//        this.repository = repository;
    }
}
