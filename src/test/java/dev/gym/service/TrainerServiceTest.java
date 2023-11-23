package dev.gym.service;

import dev.gym.config.AppConfig;
import dev.gym.model.Specialization;
import dev.gym.model.Trainer;
import dev.gym.repository.datasource.credential.CredentialGenerator;
import dev.gym.service.impl.TrainerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = AppConfig.class)
class TrainerServiceTest {

    private final TrainerService trainerService;
    private final CredentialGenerator credentialGenerator;

    @Autowired
    TrainerServiceTest(TrainerService trainerService,
                       CredentialGenerator credentialGenerator) {
        this.trainerService = trainerService;
        this.credentialGenerator = credentialGenerator;
    }

    @Test
    void testCRUD() {
        // fields values
        String firstName = "John";
        String lastName = "Doe";
        boolean isActive = true;
        Specialization specialization = new Specialization("Bodybuilding");

        // Create a new trainer with Builder
        Trainer trainer = new Trainer(firstName, lastName, credentialGenerator, isActive, specialization);
        // Save the trainer
        Trainer savedTrainer = trainerService.save(trainer);

        // Find the trainer by id
        boolean present = trainerService.getById(savedTrainer.getId()).isPresent();
        assertTrue(present);

        // Find all trainers
        assertFalse(trainerService.getAll().isEmpty());

        // Update the trainer
        savedTrainer.setFirstName("Jane");
        trainerService.save(savedTrainer);

        String trainerFirstName = trainerService.getById(savedTrainer.getId()).get().getFirstName();
        Assertions.assertEquals("Jane", trainerFirstName);

        // Delete the trainer by id
        assertThrows(UnsupportedOperationException.class, () -> trainerService.deleteById(1L));

    }

    @Test
    void givenWrongArguments_whenCreate_thenThrowIllegalArgumentException(){
        // fields values
        Trainer trainer = new Trainer(null, null, credentialGenerator, true, null);

        // Save the trainer
        assertThrows(IllegalArgumentException.class, () -> trainerService.save(trainer));

    }

    @Test
    void givenWrongId_whenFindById_thenReturnOptionalEmpty(){
        assertEquals(Optional.empty(), trainerService.getById(null));
    }
}
