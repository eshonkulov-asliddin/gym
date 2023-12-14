package dev.gym.service;

import dev.gym.config.AppConfig;
import dev.gym.service.dto.TraineeDtoRequest;
import dev.gym.service.dto.TraineeDtoResponse;
import dev.gym.service.impl.TraineeService;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = AppConfig.class)
class TraineeServiceIT {

    private final TraineeService traineeService;

    @Autowired
    TraineeServiceIT(TraineeService traineeService) {
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
        TraineeDtoRequest traineeDtoRequest = new TraineeDtoRequest(firstName, lastName, dateOfBirth, address);

        // Save the trainee
        TraineeDtoResponse traineeDtoResponse = traineeService.save(traineeDtoRequest);

        // find the trainee by username
        String username = traineeDtoResponse.username();
        Optional<TraineeDtoResponse> savedTrainee = traineeService.getByUsername(username);

        // Assert the trainee is present
        assertTrue(savedTrainee.isPresent());

        // Find all trainees
        assertTrue(traineeService.getAll().size() > 0);

        // Delete the trainee
        traineeService.deleteByUsername(username);
        assertThrows(NoResultException.class,
                () -> traineeService.getByUsername(username)
        );
    }

    @Test
    void givenIdAsNull_whenGetById_thenThrowIllegalArgumentException() {
        // fields values
        Long id = null;

        // Assert the trainee when id is null
        assertEquals(Optional.empty(), traineeService.getById(id));

    }

    @Test
    void givenWrongArguments_whenSave_thenThrowIllegalArgumentException() {
        // fields values
        TraineeDtoRequest traineeDtoRequest = new TraineeDtoRequest(null, null, null, null);

        // Assert the trainee when fields are null
        assertThrows(IllegalArgumentException.class,
                () -> traineeService.save(traineeDtoRequest)
        );
    }

}
