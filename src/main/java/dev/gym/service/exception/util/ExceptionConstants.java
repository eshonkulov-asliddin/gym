package dev.gym.service.exception.util;

public class ExceptionConstants {

    private ExceptionConstants() {
    }

    public static final String NOT_FOUND_MESSAGE = "%s with username %s not found";
    public static final String ILLIGAL_ARGUMENT_MESSAGE = "%s is not valid";
    public static final String SAVE_FAILED_MESSAGE = "couldn't save %s with id %s";
    public static final String INVALID_USERNAME_OR_PASSWORD_MESSAGE = "invalid username or token";
    public static final String ACCOUNT_BLOCKED_MESSAGE = "Your account has been blocked for 5 minutes due to 3 failed attempts.";

}
