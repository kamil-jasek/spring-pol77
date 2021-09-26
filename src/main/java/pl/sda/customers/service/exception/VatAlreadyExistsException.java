package pl.sda.customers.service.exception;

import lombok.NonNull;

public final class VatAlreadyExistsException extends BusinessServiceException {

    public VatAlreadyExistsException(@NonNull String message) {
        super(message);
    }
}
