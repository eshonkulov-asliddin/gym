package dev.gym.service.impl;

import dev.gym.GymApplication;
import dev.gym.repository.model.enums.TrainingTypeEnum;
import dev.gym.service.TrainerService;
import dev.gym.service.dto.RegisterTrainerDto;
import dev.gym.service.dto.TrainerDto;
import dev.gym.service.dto.UserDto;
import dev.gym.service.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {GymApplication.class})
class TrainerServiceImplIT {

    private final TrainerService trainerService;

    @Autowired
    TrainerServiceImplIT(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Test
    void testCRUD() {
        // fields values
        String firstName = randomAlphabetic(10);
        String lastName = randomAlphabetic(10);

        // Create a new trainerDtoRequest
        RegisterTrainerDto registerTrainerDto = new RegisterTrainerDto(firstName, lastName, TrainingTypeEnum.STRENGTH.toString());;

        // Save the trainerDtoRequest
        UserDto registered = trainerService.register(registerTrainerDto);

        // find the trainer by trainerUsername
        String username = registered.username();
        Optional<TrainerDto> trainerByUsername = trainerService.getByUsername(username);

        // Check if the trainer is present
        assertTrue(trainerByUsername.isPresent());

        // Find all trainers
        List<TrainerDto> trainerCreateDtoResponseList = trainerService.getAll();
        assertTrue(trainerCreateDtoResponseList.size() > 0);

        // Delete the trainer by trainerUsername
        trainerService.deleteByUsername(username);
        assertThrows(NotFoundException.class,
                () -> trainerService.getByUsername(username)
        );
    }

    @Test
    void givenWrongId_whenFindById_thenReturnOptionalEmpty() {
        assertTrue(trainerService.getById(null).isEmpty());
    }
}
