package dev.gym.service.impl;

import dev.gym.GymApplication;
import dev.gym.service.dto.RegisterTraineeDto;
import dev.gym.service.dto.TraineeDto;
import dev.gym.service.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {GymApplication.class})
class TraineeServiceImplIT {

    private final TraineeServiceImpl traineeService;

    @Autowired
    TraineeServiceImplIT(TraineeServiceImpl traineeService) {
        this.traineeService = traineeService;
    }

    @Test
    void testCRUD() {
        // fields values
        String firstName = randomAlphabetic(10);
        String lastName = randomAlphabetic(10);
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        String address = randomAlphabetic(5);

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
        assertEquals(Optional.empty(), traineeService.getByUsername(username));
    }

    @Test
    void givenIdAsNull_whenGetById_thenReturnEmpty() {
        // fields values
        Long id = null;

        // Assert the trainee when id is null
        assertTrue(traineeService.getById(id).isEmpty());
    }
}
