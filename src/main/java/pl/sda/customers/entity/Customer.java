package pl.sda.customers.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "customer_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Customer {

    @Id
    private UUID id;
    private String email;

    @OneToMany
    private List<Address> addresses;

    protected Customer(@NonNull String email) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.addresses = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return id.equals(customer.id) && email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
