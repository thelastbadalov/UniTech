package az.company.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public User(String firstName, String lastName, LocalDate birthDate, City city, String address, String pin, String password, LocalDateTime creationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.city = city;
        this.address = address;
        this.pin = pin;
        this.password = password;
        this.creationDate = creationDate;
    }

    private City city;
    private String address;
    @Column(unique = true)
    private String pin;

    private String password;

    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "userId")
    private Set<Account> accounts;
}
