package com.geeksforless.fintech.exception;

/**
 * Exception indicating that the amount specified for a transfer is invalid.
 * This exception is typically thrown when attempting to initiate a transfer with
 * an amount that doesn't meet the business rules (e.g., negative amounts).
 */
public class InvalidTransferAmountException extends RuntimeException {
    public InvalidTransferAmountException(String message) {
        super(message);
    }
}
