package pl.sda.customers.entity;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@DiscriminatorValue("COMPANY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends Customer {

    private String name;
    private String vat;

    public Company(String email, @NonNull String name, @NonNull String vat) {
        super(email);
        this.name = name;
        this.vat = vat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Company company = (Company) o;
        return name.equals(company.name) && vat.equals(company.vat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, vat);
    }
}
