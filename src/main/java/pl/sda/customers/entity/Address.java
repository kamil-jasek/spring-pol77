package pl.sda.customers.entity;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Entity
@Table(name = "addresses")
@Getter
@EqualsAndHashCode
public final class Address {

    @Id
    private UUID id;
    private String street;
    private String city;
    private String zipCode;
    private String countryCode;

    // only for hibernate
    private Address() {}

    public Address(@NonNull String street,
        @NonNull String city,
        @NonNull String zipCode,
        @NonNull String countryCode) {
        this.id = UUID.randomUUID();
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.countryCode = countryCode;
    }
}
