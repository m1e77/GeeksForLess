package com.geeksforless.fintech.exception;

/**
 * Exception indicating that a specific resource was not found.
 * This can be used in various scenarios like looking up entities by ID, searching
 * for data that doesn't exist, etc.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
