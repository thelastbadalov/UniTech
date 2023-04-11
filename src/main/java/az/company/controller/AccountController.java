package az.company.controller;

import az.company.dto.AccountDto;
import az.company.dto.request.CreateAccountRequest;
import az.company.dto.request.TransferDetailsRequest;
import az.company.dto.request.UpdateAccountStatusRequest;
import az.company.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/account")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/createAccount")
    @Operation(summary = "Create Account", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountRequest request) {
        return new ResponseEntity<>(service.createAccount(request), HttpStatus.CREATED);
    }

    @PutMapping("/updateAccountStatus/{accountId}")
    @Operation(summary = "Update Account Status", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<AccountDto> updateAccountStatus(@PathVariable(name = "accountId") String accountId,
                                                          @RequestParam UpdateAccountStatusRequest request) {
        return ResponseEntity.ok(service.updateAccountStatus(accountId, request));
    }

    @GetMapping("/getAllActiveAccounts/{userId}")
    @Operation(summary = "Find All Active Accounts of User", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<AccountDto>> getAllActiveAccounts(@PathVariable String userId) {
        return ResponseEntity.ok(service.getAllActiveAccountsOfUser(userId));
    }

    @PutMapping("/transferMoney/{accountId}")
    @Operation(summary = "Transfer money", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> transferMoney(@PathVariable String accountId, @RequestBody TransferDetailsRequest request) {
        return ResponseEntity.ok(service.transferMoney(accountId, request));
    }
}
