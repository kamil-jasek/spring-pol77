package pl.sda.customers.entity;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pl.sda.customers.service.dto.RegisterPersonForm;

@Entity
@DiscriminatorValue("PERSON")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // for hibernate
@Getter
public final class Person extends Customer {

    private String firstName;
    private String lastName;
    private String pesel;

    public Person(String email, @NonNull String firstName, @NonNull String lastName, @NonNull String pesel) {
        super(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }

    public static Person createWith(RegisterPersonForm form) {
        return new Person(form.getEmail(),
            form.getFirstName(),
            form.getLastName(),
            form.getPesel());
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
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
        Person person = (Person) o;
        return firstName.equals(person.firstName) && lastName.equals(person.lastName) && pesel.equals(person.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, pesel);
    }
}
