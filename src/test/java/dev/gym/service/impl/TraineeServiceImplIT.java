package dev.gym.service.impl;

import dev.gym.repository.config.RepositoryConfig;
import dev.gym.security.config.SecurityConfig;
import dev.gym.service.config.ServiceConfig;
import dev.gym.service.dto.RegisterTraineeDto;
import dev.gym.service.dto.TraineeDto;
import dev.gym.service.dto.UserDto;
import dev.gym.service.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {ServiceConfig.class, RepositoryConfig.class, SecurityConfig.class})
class TraineeServiceImplIT {

    private final TraineeServiceImpl traineeService;

    @Autowired
    TraineeServiceImplIT(TraineeServiceImpl traineeService) {
        this.traineeService = traineeService;
    }

    @Test
    void testCRUD() {
        // fields values
        String firstName = "John";
        String lastName = "Doe";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        String address = "123 Main St.";

        // Create a new traineeDtoRequest
        RegisterTraineeDto registerTraineeDto = new RegisterTraineeDto(firstName, lastName, dateOfBirth, address);

        // Save the trainee
        UserDto registered = traineeService.register(registerTraineeDto);

        // find the trainee by trainerUsername
        String username = registered.username();
        Optional<TraineeDto> savedTrainee = traineeService.getByUsername(username);

        // Assert the trainee is present
        assertTrue(savedTrainee.isPresent());

        // Find all trainees
        assertTrue(traineeService.getAll().size() > 0);

        // Delete the trainee
        traineeService.deleteByUsername(username);
        assertThrows(NotFoundException.class,
                () -> traineeService.getByUsername(username)
        );
    }

    @Test
    void givenIdAsNull_whenGetById_thenReturnEmpty() {
        // fields values
        Long id = null;

        // Assert the trainee when id is null
        assertTrue(traineeService.getById(id).isEmpty());
    }
}
