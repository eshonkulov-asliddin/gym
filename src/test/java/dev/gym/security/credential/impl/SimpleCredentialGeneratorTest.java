package dev.gym.security.credential.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimpleCredentialGeneratorTest {

    @Mock
    private EntityManagerFactory entityManagerFactory;
    @Mock
    private EntityManager entityManager;
    @Mock
    private TypedQuery<String> query;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private SimpleCredentialGenerator simpleCredentialGenerator;

    @BeforeEach
    void setUp() {
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
    }

    @Test
    void givenFirstNameAndLastName_whenGenerateUsernameAndPassword_thenReturnUniqueUsernameAndPassword() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String expectedUsername = "John.Doe3";

        when(entityManager.createQuery(anyString(), eq(String.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of("John.Doe", "John.Doe1", "John.Doe2"));
        when(passwordEncoder.encode(anyString())).thenReturn("password");

        // Act
        String generatedUsername = simpleCredentialGenerator.generateUsername(firstName, lastName);
        String generatedPassword = simpleCredentialGenerator.generatePassword();

        // Assert
        assertEquals(expectedUsername, generatedUsername);
        assertNotNull(generatedPassword);
    }

}
