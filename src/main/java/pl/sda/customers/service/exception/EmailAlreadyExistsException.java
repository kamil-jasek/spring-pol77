package pl.sda.customers.service.exception;

import lombok.NonNull;

public final class EmailAlreadyExistsException extends BusinessServiceException {

    public EmailAlreadyExistsException(@NonNull String message) {
        super(message);
    }
}
