package dev.gym.model;

import dev.gym.repository.datasource.credential.CredentialGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainerTest {

    private Trainer trainer;
    private Specialization specialization;
    private CredentialGenerator credentialGenerator;

    @BeforeEach
    void setUp(){

        credentialGenerator = mock(CredentialGenerator.class);
        specialization = new Specialization("Fitness");

        // Mocking credentialGenerator behavior
        when(credentialGenerator.generateUsername("John", "Doe")).thenReturn("john.doe");
        when(credentialGenerator.generatePassword()).thenReturn("securePassword");

        // Creating a Trainer
        trainer = new Trainer("John", "Doe", credentialGenerator, true, specialization);
    }

    @Test
    void testTrainerConstructor() {

        // Asserting values
        assertEquals("John", trainer.getFirstName());
        assertEquals("Doe", trainer.getLastName());
        assertEquals("john.doe", trainer.getUsername());
        assertEquals("securePassword", trainer.getPassword());
        assertTrue(trainer.isActive());
        assertEquals(1L, trainer.getId().longValue()); // Assuming the ID generator starts from 1
        assertEquals(specialization, trainer.getSpecialization());
    }

    @Test
    void testUpdateTrainer(){
        String FIRST_NAME_UPDATED = "Tom";
        String LAST_NAME_UPDATED = "Jerry";
        String USERNAME_UPDATED = "tom.jerry";
        String PASSWORD_UPDATED = "securePassword";
        boolean IS_ACTIVE_UPDATED = false;
        Specialization SPECIALIZATION_UPDATED = new Specialization("Yoga");

        trainer.setFirstName(FIRST_NAME_UPDATED);
        trainer.setLastName(LAST_NAME_UPDATED);
        trainer.setUsername(USERNAME_UPDATED);
        trainer.setActive(IS_ACTIVE_UPDATED);
        trainer.setPassword(PASSWORD_UPDATED);
        trainer.setSpecialization(SPECIALIZATION_UPDATED);

        // Asserting values
        assertEquals(FIRST_NAME_UPDATED, trainer.getFirstName());
        assertEquals(LAST_NAME_UPDATED, trainer.getLastName());
        assertEquals(USERNAME_UPDATED, trainer.getUsername());
        assertEquals(IS_ACTIVE_UPDATED, trainer.isActive());
        assertEquals(SPECIALIZATION_UPDATED, trainer.getSpecialization());
    }

    @Test
    void testHashCodeEquals(){
        // Creating a Trainer (Id automatically generated)
        Trainer trainer1 = new Trainer("John", "Doe", credentialGenerator, true, specialization);
        Trainer trainer2 = new Trainer("John", "Doe", credentialGenerator, true, specialization);

        // Asserting values
        assertFalse(trainer1.equals(trainer2));
        assertNotEquals(trainer1.hashCode(), trainer2.hashCode());
    }

    @Test
    void testToString(){
        // Creating a Trainer
        Trainer trainer = new Trainer("John", "Doe", credentialGenerator, true, specialization);
        // Asserting values
        assertEquals(String.format("Trainer(id=%s, specialization=%s)", trainer.getId(), specialization), trainer.toString());
    }
}
