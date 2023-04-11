package az.company.exception;

public class AccountIsDeactiveException extends RuntimeException {
    public AccountIsDeactiveException(String message) {
        super(message);
    }
}
