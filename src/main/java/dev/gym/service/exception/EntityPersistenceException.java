package dev.gym.service.exception;

public class EntityPersistenceException extends RuntimeException {

    public EntityPersistenceException(String message) {
        super(message);
    }

    public EntityPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityPersistenceException(Throwable cause) {
        super(cause);
    }

}
