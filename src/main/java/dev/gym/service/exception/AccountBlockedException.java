package dev.gym.service.exception;

public class AccountBlockedException extends RuntimeException {

        public AccountBlockedException(String message) {
            super(message);
        }

        public AccountBlockedException(String message, Throwable cause) {
            super(message, cause);
        }

        public AccountBlockedException(Throwable cause) {
            super(cause);
        }
}
