package dev.gym.service.validator;

import dev.gym.service.dto.TrainingDtoRequest;
import dev.gym.service.dto.TrainingTypeDto;
import dev.gym.service.validator.impl.TrainingValidator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TrainingValidatorTest {

    private TrainingValidator trainingValidator = new TrainingValidator();

    @Test
    void givenNullTrainerId_whenValidate_thenThrowException() {
        TrainingDtoRequest request = new TrainingDtoRequest(null, 1L, "Test", new TrainingTypeDto("STRENGTH"), LocalDate.now(), 60);

        assertThrows(IllegalArgumentException.class,
                () -> trainingValidator.validate(request)
        );
    }

    @Test
    void givenNullTraineeId_whenValidate_thenThrowException() {
        TrainingDtoRequest request = new TrainingDtoRequest(1L, null, "Test", new TrainingTypeDto("STRENGTH"), LocalDate.now(), 60);

        assertThrows(IllegalArgumentException.class,
                () -> trainingValidator.validate(request)
        );
    }

    @Test
    void givenBlankTrainingName_whenValidate_thenThrowException() {
        TrainingDtoRequest request = new TrainingDtoRequest(1L, 1L, "", new TrainingTypeDto("STRENGTH"), LocalDate.now(), 60);

        assertThrows(IllegalArgumentException.class,
                () -> trainingValidator.validate(request)
        );
    }

    @Test
    void givenNullTrainingType_whenValidate_thenThrowException() {
        TrainingDtoRequest request = new TrainingDtoRequest(1L, 1L, "Test", null, LocalDate.now(), 60);

        assertThrows(IllegalArgumentException.class,
                () -> trainingValidator.validate(request)
        );
    }

    @Test
    void givenNullTrainingDate_whenValidate_thenThrowException() {
        TrainingDtoRequest request = new TrainingDtoRequest(1L, 1L, "Test", new TrainingTypeDto("STRENGTH"), null, 60);

        assertThrows(IllegalArgumentException.class,
                () -> trainingValidator.validate(request)
        );
    }

    @Test
    void givenNullTrainingDuration_whenValidate_thenThrowException() {
        TrainingDtoRequest request = new TrainingDtoRequest(1L, 1L, "Test", new TrainingTypeDto("STRENGTH"), LocalDate.now(), 0);

        assertThrows(IllegalArgumentException.class,
                () -> trainingValidator.validate(request)
        );
    }

    @Test
    void givenAllValidParameters_whenValidate_thenNoException() {
        TrainingDtoRequest request = new TrainingDtoRequest(1L, 1L, "Test", new TrainingTypeDto("STRENGTH"), LocalDate.now(), 60);

        // Should not throw an exception
        trainingValidator.validate(request);
    }
}
