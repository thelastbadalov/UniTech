package az.company.service;

import az.company.dto.AccountDto;
import az.company.dto.converter.AccountDtoConverter;
import az.company.dto.request.CreateAccountRequest;
import az.company.dto.request.TransferDetailsRequest;
import az.company.dto.request.UpdateAccountStatusRequest;
import az.company.exception.AccountIsDeactiveException;
import az.company.exception.AccountNotFoundException;
import az.company.exception.BalanceNotEnoughMoneyException;
import az.company.exception.FromAndDestianitonAccountAreSameException;
import az.company.model.Account;
import az.company.model.AccountStatus;
import az.company.model.User;
import az.company.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserService service;
    private final AccountDtoConverter converter;

    public AccountService(AccountRepository accountRepository, UserService service, AccountDtoConverter converter) {
        this.accountRepository = accountRepository;
        this.service = service;
        this.converter = converter;
    }


    public AccountDto createAccount(CreateAccountRequest request) {
        User user = service.findUserById(request.getUserId());
        Account account = new Account(
                request.getInitialCredit(),
                LocalDateTime.now(),
                AccountStatus.ACTIVE,
                user);
        return converter.convert(accountRepository.save(account));
    }

    public AccountDto updateAccountStatus(String accountId, UpdateAccountStatusRequest status) {
        Account account = findAccountById(accountId);
        account.setAccountStatus(AccountStatus.valueOf(status.name()));
        return converter.convert(accountRepository.save(account));
    }

    public List<AccountDto> getAllActiveAccountsOfUser(String userId) {
        User user = service.findUserById(userId);
        List<Account> list = accountRepository.findAllByAccountStatusEqualsAndUserId(AccountStatus.ACTIVE, user);
        return converter.convert(list);
    }

    @Transactional
    public String transferMoney(String fromAccountId, TransferDetailsRequest request) {
        Account account = findAndCheckAccountActiveOrNot(fromAccountId);
        Account destination = findAndCheckAccountActiveOrNot(request.getDestinationAccountId());
        checkFromAndDestinationAccountAreSame(fromAccountId, request.getDestinationAccountId());
        checkAccountBalance(account.getBalance(), request.getAmount());
        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);
        destination.setBalance(destination.getBalance().add(request.getAmount()));
        accountRepository.save(destination);
        return "Transfer completed successfully";


    }


    private Account findAndCheckAccountActiveOrNot(String accountId) {
        findAccountById(accountId);
        return accountRepository.findByIdAndAccountStatusEquals(accountId, AccountStatus.ACTIVE).orElseThrow(
                () -> new AccountIsDeactiveException("Account is deactive with accountId : " + accountId));
    }

    private Account findById(String accountId) {
        return accountRepository.findById(accountId).orElseThrow(
                () -> new AccountNotFoundException("can not find account with given id " + accountId));
    }

    protected Account findAccountById(String accountId) {
        return accountRepository.findById(accountId).orElseThrow(
                () -> new AccountNotFoundException(" can not find Account with given id : " + accountId));
    }

    private void checkFromAndDestinationAccountAreSame(String accountId, String destinationId) {
        if (accountId.equals(destinationId)) {
            throw new FromAndDestianitonAccountAreSameException("you can not send money to same account ! ");
        }
    }

    private void checkAccountBalance(BigDecimal balance, BigDecimal transferAmount) {
        if (balance.compareTo(transferAmount) < 0) {
            throw new BalanceNotEnoughMoneyException("Not have" + transferAmount + "in account balance: " + balance);
        }
    }
}

