package project.runner.exceptions;

public class SellerProfileAlreadyExistsException extends RuntimeException {
    public SellerProfileAlreadyExistsException(String message) {
        super(message);
    }
}
