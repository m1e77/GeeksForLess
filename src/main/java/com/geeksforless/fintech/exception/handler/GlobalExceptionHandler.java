package com.geeksforless.fintech.exception.handler;

import com.geeksforless.fintech.exception.InsufficientFundsException;
import com.geeksforless.fintech.exception.InvalidTransferAmountException;
import com.geeksforless.fintech.exception.NotFoundException;
import com.geeksforless.fintech.exception.ServerIsOverloadedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * A centralized exception handler that captures exceptions thrown across various controllers and
 * provides meaningful HTTP responses to the client.
 * <p>
 * This class uses the {@link ControllerAdvice} annotation, indicating it's a global exception handler.
 * Exception handlers defined in this class will be applied to all controllers in the application.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the {@link NotFoundException} by returning a 404 Not Found HTTP status.
     *
     * @param ex The caught {@link NotFoundException}.
     * @return A {@link ResponseEntity} with the exception message and a 404 Not Found HTTP status.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link InsufficientFundsException} by returning a 400 Bad Request HTTP status.
     *
     * @param ex The caught {@link InsufficientFundsException}.
     * @return A {@link ResponseEntity} with the exception message and a 400 Bad Request HTTP status.
     */
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleInsufficientFundsException(InsufficientFundsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the {@link InvalidTransferAmountException} by returning a 400 Bad Request HTTP status.
     *
     * @param ex The caught {@link InvalidTransferAmountException}.
     * @return A {@link ResponseEntity} with the exception message and a 400 Bad Request HTTP status.
     */
    @ExceptionHandler(InvalidTransferAmountException.class) // Corrected the exception type here.
    public ResponseEntity<String> handleInvalidTransferAmountException(InvalidTransferAmountException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the {@link ServerIsOverloadedException} by returning a 500  HTTP status.
     *
     * @param ex The caught {@link ServerIsOverloadedException}.
     * @return A {@link ResponseEntity} with the exception message and a 500 HTTP status.
     */
    @ExceptionHandler(ServerIsOverloadedException.class) // Corrected the exception type here.
    public ResponseEntity<String> handleServerIsOverloadedException(ServerIsOverloadedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


