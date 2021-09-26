package pl.sda.customers.demo;

import org.springframework.stereotype.Component;

@Component
public class InvoiceService {

    public void createInvoice(String number) {
        System.out.println("creating invoice: " + number);
    }
}
