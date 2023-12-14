package dev.gym.repository.datasource.credential.impl;

import dev.gym.repository.datasource.credential.CredentialGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class SimpleCredentialGenerator implements CredentialGenerator {

    public static final String DELIMITER = ".";

    public final Logger logger = LoggerFactory.getLogger(SimpleCredentialGenerator.class);

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + DELIMITER + lastName;
        HashSet<String> usernameSet = new HashSet<>();
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            String query = "SELECT u.username FROM User u WHERE u.username LIKE :username";
            usernameSet = new HashSet<>(entityManager.createQuery(query, String.class)
                    .setParameter("username", baseUsername + "%")
                    .getResultList());
        } catch (PersistenceException e) {
            logger.error("Error while generating username", e);
            e.printStackTrace();
        }
        int count = 0;
        String generatedUsername = baseUsername;
        while (usernameSet.contains(generatedUsername)) {
            count++;
            generatedUsername = baseUsername + count;
        }

        return generatedUsername;
    }

    @Override
    public String generatePassword() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}
