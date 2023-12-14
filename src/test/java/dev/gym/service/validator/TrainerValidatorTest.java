package dev.gym.service.validator;

import dev.gym.service.dto.TrainerDtoRequest;
import dev.gym.service.dto.TrainingTypeDto;
import dev.gym.service.validator.impl.TrainerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TrainerValidatorTest {

    private TrainerValidator trainerValidator;

    @BeforeEach
    void setUp() {
        trainerValidator = new TrainerValidator();
    }

    @Test
    void givenValidTrainerDtoRequest_whenValidate_thenSuccess() {
        TrainerDtoRequest trainerDtoRequest = new TrainerDtoRequest("firstName", "lastName", new TrainingTypeDto("STRENGTH"));
        trainerValidator.validate(trainerDtoRequest);
    }

    @Test
    void givenTrainerDtoRequest_whenSpecializationEmpty_thenThrowIllegalArgumentException() {
        TrainerDtoRequest trainerDtoRequest = new TrainerDtoRequest("firstName", "lastName", null);
        assertThrows(IllegalArgumentException.class,
                () -> trainerValidator.validate(trainerDtoRequest)
        );
    }

    @Test
    void givenTrainerDtoRequest_whenFirstNameEmptyOrBlank_thenThrowIllegalArgumentException() {
        // firstName is empty
        TrainerDtoRequest dtoWithNullFirstName = new TrainerDtoRequest(null, "lastName", new TrainingTypeDto("STRENGTH"));
        assertThrows(IllegalArgumentException.class,
                () -> trainerValidator.validate(dtoWithNullFirstName)
        );

        // firstName is blank
        TrainerDtoRequest dtoWithBlankFirstName = new TrainerDtoRequest("", "lastName", new TrainingTypeDto("STRENGTH"));
        assertThrows(IllegalArgumentException.class,
                () -> trainerValidator.validate(dtoWithBlankFirstName)
        );
    }

    @Test
    void givenTrainerDtoRequest_whenLastNameEmptyOrBlank_thenThrowIllegalArgumentException() {
        // lastname is empty
        TrainerDtoRequest dtoWithNullLastName = new TrainerDtoRequest("firstname", null, new TrainingTypeDto("STRENGTH"));
        assertThrows(IllegalArgumentException.class,
                () -> trainerValidator.validate(dtoWithNullLastName)
        );

        // lastName is blank
        TrainerDtoRequest dtoWithEmptyLastName = new TrainerDtoRequest("firstName", "", new TrainingTypeDto("STRENGTH"));
        assertThrows(IllegalArgumentException.class,
                () -> trainerValidator.validate(dtoWithEmptyLastName)
        );
    }
}
