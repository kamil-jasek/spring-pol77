package pl.sda.customers.controller;

import java.time.Instant;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.sda.customers.service.exception.BusinessServiceException;
import pl.sda.customers.service.exception.CustomerNotExistsException;

@ControllerAdvice
final class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessServiceException.class)
    ResponseEntity<ErrorMessage> handleException(BusinessServiceException ex) {
        return ResponseEntity.badRequest() // -> status code = 400
            .body(new ErrorMessage(ex.getMessage(), Instant.now())); // JSON -> { message: "....", time: "..." }
    }

    @ExceptionHandler(CustomerNotExistsException.class)
    ResponseEntity<ErrorMessage> handleException(CustomerNotExistsException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND) // -> status code = 404
            .body(new ErrorMessage(ex.getMessage(), Instant.now()));
    }

    @Value
    private static class ErrorMessage {
        String message;
        Instant time;
    }
}
