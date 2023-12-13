package dev.gym.service;

import dev.gym.config.AppConfig;
import dev.gym.service.dto.TrainerDtoRequest;
import dev.gym.service.dto.TrainerDtoResponse;
import dev.gym.service.dto.TrainingTypeDto;
import dev.gym.service.impl.TrainerService;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = AppConfig.class)
class TrainerServiceIT {

    private final TrainerService trainerServiceImpl;

    @Autowired
    TrainerServiceIT(TrainerService trainerServiceImpl) {
        this.trainerServiceImpl = trainerServiceImpl;
    }

    @Test
    void testCRUD() {
        // fields values
        String firstName = "John";
        String lastName = "Doe";

        TrainingTypeDto specialization = new TrainingTypeDto("STRENGTH");

        // Create a new trainerDtoRequest
        TrainerDtoRequest trainerDtoRequest = new TrainerDtoRequest(firstName, lastName, specialization);

        // Save the trainerDtoRequest
        TrainerDtoResponse trainerDtoResponse = trainerServiceImpl.save(trainerDtoRequest);

        // find the trainer by username
        String username = trainerDtoResponse.username();
        Optional<TrainerDtoResponse> trainerByUsername = trainerServiceImpl.getByUsername(username);

        // Check if the trainer is present
        assertTrue(trainerByUsername.isPresent());

        // Find all trainers
        List<TrainerDtoResponse> trainerDtoResponseList = trainerServiceImpl.getAll();
        assertTrue(trainerDtoResponseList.size() > 0);

        // Delete the trainer by username
        trainerServiceImpl.deleteByUsername(username);
        assertThrows(NoResultException.class, () -> trainerServiceImpl.getByUsername(username));
    }

    @Test
    void givenWrongArguments_whenCreate_thenThrowIllegalArgumentException() {
        // fields values
        TrainerDtoRequest trainer = new TrainerDtoRequest(null, null, null);

        // Save the trainer
        assertThrows(IllegalArgumentException.class, () -> trainerServiceImpl.save(trainer));

    }

    @Test
    void givenWrongId_whenFindById_thenReturnOptionalEmpty() {
        assertEquals(Optional.empty(), trainerServiceImpl.getById(null));
    }

}