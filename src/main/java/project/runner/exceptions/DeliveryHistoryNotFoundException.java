package project.runner.exceptions;

public class DeliveryHistoryNotFoundException extends RuntimeException {
    public DeliveryHistoryNotFoundException(String message) {
        super(message);
    }
}
