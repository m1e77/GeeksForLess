package com.geeksforless.fintech.exception;

/**
 * Exception indicating that the server is currently overloaded.
 * <p>
 * This exception can be thrown when the server cannot handle
 * the current volume of requests or tasks and is likely to indicate
 * a temporary condition. The client or caller might consider
 * retrying the operation after some delay.
 * </p>
 */
public class ServerIsOverloadedException extends RuntimeException {
    public ServerIsOverloadedException(String message) {
        super(message);
    }
}
