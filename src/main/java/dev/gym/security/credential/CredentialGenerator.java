package dev.gym.security.credential;

public interface CredentialGenerator {

    String generateUsername(String firstName, String lastName);

    String generatePassword();
}
