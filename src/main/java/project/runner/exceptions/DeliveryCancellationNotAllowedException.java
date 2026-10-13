package project.runner.exceptions;

public class DeliveryCancellationNotAllowedException extends RuntimeException {
    public DeliveryCancellationNotAllowedException(String message) {
        super(message);
    }
}
