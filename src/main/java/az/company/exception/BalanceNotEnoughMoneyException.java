package az.company.exception;

public class BalanceNotEnoughMoneyException extends RuntimeException {
    public BalanceNotEnoughMoneyException(String message) {
        super(message);
    }
}
