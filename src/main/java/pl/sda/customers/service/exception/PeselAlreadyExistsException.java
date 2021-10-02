package pl.sda.customers.service.exception;

public final class PeselAlreadyExistsException extends BusinessServiceException {

    public PeselAlreadyExistsException(String message) {
        super(message);
    }
}
