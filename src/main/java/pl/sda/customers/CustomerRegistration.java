package pl.sda.customers;

import org.springframework.stereotype.Component;

@Component
public class CustomerRegistration {

    private CustomerRepository repository;

    public CustomerRegistration(CustomerRepository repository) {
        this.repository = repository;
    }

    public void register(String email, String name) {
        System.out.println("registering customer: " + email);
        repository.save(email, name);
        System.out.println("registered customer: " + email);
    }
}
