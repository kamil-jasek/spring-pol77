package pl.sda.customers;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.sda.customers.entity.Address;
import pl.sda.customers.entity.Company;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.entity.Person;

@SpringBootApplication
public class CustomersApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomersApplication.class, args);
	}

	@Component
	@RequiredArgsConstructor
	@Profile("dev")
	static class InitOnStartup {

		private final CustomerRepository repository;

		@EventListener
		@Transactional
		public void setup(ApplicationReadyEvent event) {
			final var customer1 = new Person("ak@wp.pl", "Jan", "Nowak", "92929929929");
			final var customer2 = new Person("qw@wp.pl", "Jan", "Kowalski", "83838288233");
			final var customer3 = new Person("cx@wp.pl", "Janeczek", "Nowaczkiewicz", "83838288233");
			final var customer4 = new Company("er@on.pl", "Comp S.A.", "9393993944");

			customer1.addAddress(new Address("str", "Wawa", "04-333", "PL"));
			customer2.addAddress(new Address("str", "Kraków", "33-220", "PL"));
			customer2.addAddress(new Address("str", "Wawa", "44-300", "PL"));
			customer3.addAddress(new Address("str", "Wrocław", "55-200", "PL"));
			customer4.addAddress(new Address("str2", "Kraków", "33-220", "PL"));

			repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));
		}
	}
}
