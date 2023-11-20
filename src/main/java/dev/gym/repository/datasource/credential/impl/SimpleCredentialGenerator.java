package dev.gym.repository.datasource.credential.impl;

import dev.gym.repository.datasource.credential.CredentialGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SimpleCredentialGenerator implements CredentialGenerator {
    private final Map<String, Integer> usernameStore;

    @Autowired
    public SimpleCredentialGenerator(Map<String, Integer> usernameStore) {
        this.usernameStore = usernameStore;
    }

    @Override
    public String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
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
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder passwordBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * chars.length());
            passwordBuilder.append(chars.charAt(index));
        }
        return passwordBuilder.toString();
    }
}
