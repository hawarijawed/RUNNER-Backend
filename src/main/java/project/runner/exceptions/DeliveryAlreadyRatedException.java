package project.runner.exceptions;

public class DeliveryAlreadyRatedException extends RuntimeException {
    public DeliveryAlreadyRatedException(String message) {
        super(message);
    }
}
