package project.runner.exceptions;

public class DeliveryAlreadyAcceptedException extends RuntimeException {
    public DeliveryAlreadyAcceptedException(String message) {
        super(message);
    }
}
