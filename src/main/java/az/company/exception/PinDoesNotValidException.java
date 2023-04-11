package az.company.exception;

public class PinDoesNotValidException extends RuntimeException {
    public PinDoesNotValidException(String message) {
        super(message);
    }
}
