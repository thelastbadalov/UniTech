package az.company.dto;

import az.company.model.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String id;

    private BigDecimal balance;

    private LocalDateTime createdDateTime;
    private AccountStatus accountStatus;

}
