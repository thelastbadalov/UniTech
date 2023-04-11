package az.company.exception;

public class PasswordDoesNotValidException extends RuntimeException {
    public PasswordDoesNotValidException(String message) {
        super(message);
    }
}
