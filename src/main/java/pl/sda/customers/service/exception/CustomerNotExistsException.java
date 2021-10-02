package pl.sda.customers.service.exception;

public final class CustomerNotExistsException extends BusinessServiceException {

    public CustomerNotExistsException(String message) {
        super(message);
    }
}
