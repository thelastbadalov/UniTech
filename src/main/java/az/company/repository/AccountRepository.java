package az.company.repository;

import az.company.model.Account;
import az.company.model.AccountStatus;
import az.company.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findAllByAccountStatusEqualsAndUserId(AccountStatus accountStatus, User user);

    Optional<Account> findByIdAndAccountStatusEquals(String accountId, AccountStatus status);
}
