package dev.gym.repository.datasource.credential;

public interface CredentialGenerator {

    String generateUsername(String firstName, String lastName);

    String generatePassword();
}