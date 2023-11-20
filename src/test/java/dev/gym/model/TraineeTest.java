package dev.gym.model;

import dev.gym.repository.datasource.credential.CredentialGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class TraineeTest {

    private Trainee trainee;
    private CredentialGenerator credentialGenerator;

    @BeforeEach
    void setUp() {
        credentialGenerator = mock(CredentialGenerator.class);

        // Mocking credentialGenerator behavior
        when(credentialGenerator.generateUsername("John", "Doe")).thenReturn("john.doe");
        when(credentialGenerator.generatePassword()).thenReturn("securePassword");

        // Creating a Trainee using the Builder
        trainee = new Trainee.Builder("John", "Doe", credentialGenerator, true)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .build();
    }

    @Test
    void testTraineeBuilder() {
        // Asserting values
        assertEquals("John", trainee.getFirstName());
        assertEquals("Doe", trainee.getLastName());
        assertEquals("john.doe", trainee.getUsername());
        assertEquals("securePassword", trainee.getPassword());
        assertTrue(trainee.isActive());
        assertEquals(LocalDate.of(1990, 1, 1), trainee.getDateOfBirth());
        assertEquals("123 Main St", trainee.getAddress());
        assertEquals(trainee.getId(), trainee.getId().longValue()); // Assuming the ID generator starts from 1
    }

    @Test
    void testUpdateTrainee(){
        String FIRST_NAME_UPDATED = "Tom";
        String LAST_NAME_UPDATED = "Jerry";
        String ADDRESS_UPDATED = "456 Main St";
        LocalDate DATE_OF_BIRTH_UPDATED = LocalDate.of(1991, 1, 1);

        trainee.setFirstName(FIRST_NAME_UPDATED);
        trainee.setLastName(LAST_NAME_UPDATED);
        trainee.setAddress(ADDRESS_UPDATED);
        trainee.setDateOfBirth(DATE_OF_BIRTH_UPDATED);

        // Asserting values
        assertEquals(FIRST_NAME_UPDATED, trainee.getFirstName());
        assertEquals(LAST_NAME_UPDATED, trainee.getLastName());
        assertEquals(ADDRESS_UPDATED, trainee.getAddress());
        assertEquals(DATE_OF_BIRTH_UPDATED, trainee.getDateOfBirth());
    }

    @Test
    void testHashCodeEquals(){

        // Creating a Trainee using the Builder (Id automatically generated)
        Trainee trainee1 = new Trainee.Builder("John", "Doe", credentialGenerator, true)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .build();

        // Creating a Trainee using the Builder
        Trainee trainee2 = new Trainee.Builder("John", "Doe", credentialGenerator, true)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .build();

        // Then
        assertFalse(trainee1.equals(trainee2));
        assertNotEquals(trainee1.hashCode(), trainee2.hashCode());
    }

    @Test
    void testToString(){
        // Creating a Trainee using the Builder
        Trainee trainee = new Trainee.Builder("John", "Doe", credentialGenerator, true)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .build();

        // Asserting values
        assertEquals(String.format("Trainee(id=%s, dateOfBirth=%s, address=%s)",
                trainee.getUserId(),
                trainee.getDateOfBirth(),
                trainee.getAddress()), trainee.toString());
    }

}
