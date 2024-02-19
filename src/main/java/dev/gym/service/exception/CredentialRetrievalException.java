package dev.gym.service.exception;

public class CredentialRetrievalException extends RuntimeException {

    public CredentialRetrievalException(String message) {
        super(message);
    }

    public CredentialRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}
