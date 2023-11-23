package dev.gym.repository.datasource.credential.impl;

import dev.gym.repository.datasource.credential.CredentialGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SimpleCredentialGenerator implements CredentialGenerator {
    public static final String DELIMITER = ".";
    private final Map<String, Integer> usernameStore;

    @Autowired
    public SimpleCredentialGenerator(Map<String, Integer> usernameStore) {
        this.usernameStore = usernameStore;
    }

    @Override
    public String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + DELIMITER + lastName;
        int count = usernameStore.getOrDefault(baseUsername, 0);

        String generatedUsername = (count == 0) ? baseUsername : baseUsername + count;

        while (usernameStore.containsKey(generatedUsername)){
            count++;
            generatedUsername = baseUsername + count;
        }

        usernameStore.put(generatedUsername, 1);
        return generatedUsername;
    }

    @Override
    public String generatePassword() {
        return RandomStringUtils.randomAlphabetic (10);
    }
}
