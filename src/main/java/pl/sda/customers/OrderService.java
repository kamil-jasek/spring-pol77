package pl.sda.customers;

import org.springframework.stereotype.Component;

@Component
public class OrderService {

    private final InvoiceService invoiceService;
    private final OrderRepository repository;

    public OrderService(InvoiceService invoiceService, OrderRepository repository) {
        this.invoiceService = invoiceService;
        this.repository = repository;
    }

    public void makeOrder(String number) {
        System.out.println("making order: " + number);
        invoiceService.createInvoice(number);
        repository.save(number);
        System.out.println("order created");
    }
}
