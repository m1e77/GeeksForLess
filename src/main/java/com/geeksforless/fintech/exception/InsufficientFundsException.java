package com.geeksforless.fintech.exception;

/**
 * Exception indicating that there are not enough funds in an account for a specific operation.
 * This exception is typically thrown when attempting to make a withdrawal or transfer
 * that exceeds the current balance of an account.
 */
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
