package pl.sda.customers.demo;

import org.springframework.stereotype.Component;

@Component
public class OrderRepository {

    public void save(String number) {
        System.out.println("saving order: " + number);
    }
}
