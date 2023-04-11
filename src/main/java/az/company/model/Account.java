package az.company.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Account {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    public Account(String id, BigDecimal balance, LocalDateTime creationDate,
                   AccountStatus status, User userId) {
        this.id = id;
        this.balance = balance;
        this.createdDateTime = creationDate;
        this.accountStatus = status;
        this.userId = userId;
    }
    private BigDecimal balance;

private LocalDateTime createdDateTime;
@Enumerated(EnumType.STRING)
private AccountStatus accountStatus;

@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
 private User userId;

    public Account(BigDecimal balance, LocalDateTime creationDate,
                   AccountStatus status, User userId) {
        this.balance = balance;
        this.createdDateTime = creationDate;
        this.accountStatus = status;
        this.userId = userId;
    }

}
