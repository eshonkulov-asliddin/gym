package dev.gym.service.impl;

import dev.gym.repository.config.RepositoryConfig;
import dev.gym.repository.model.enums.TrainingTypeEnum;
import dev.gym.security.config.SecurityConfig;
import dev.gym.service.TrainerService;
import dev.gym.service.config.ServiceConfig;
import dev.gym.service.dto.RegisterTrainerDto;
import dev.gym.service.dto.TrainerDto;
import dev.gym.service.dto.UserDto;
import dev.gym.service.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {ServiceConfig.class, RepositoryConfig.class, SecurityConfig.class})
class TrainerServiceImplIT {

    private final TrainerService trainerService;

    @Autowired
    TrainerServiceImplIT(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Test
    void testCRUD() {
        // fields values
        String firstName = "John";
        String lastName = "Doe";

        // Create a new trainerDtoRequest
        RegisterTrainerDto registerTrainerDto = new RegisterTrainerDto(firstName, lastName, TrainingTypeEnum.CARDIO.toString());;

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
