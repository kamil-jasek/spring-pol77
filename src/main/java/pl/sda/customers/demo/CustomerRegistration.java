package pl.sda.customers.demo;

import org.springframework.stereotype.Component;

@Component
public class CustomerRegistration {

//    @Autowired -- not recommended
    private final CustomerDatabase repository;

//    @Autowired -- not needed
    public CustomerRegistration(CustomerDatabase repository) {
        this.repository = repository;
    }

    public void register(String email, String name) {
        System.out.println("registering customer: " + email);
        repository.save(email, name);
        System.out.println("registered customer: " + email);
    }

//    @Autowired -- not recommended
//    public void setRepository(CustomerRepository repository) {
//        this.repository = repository;
//    }
}
