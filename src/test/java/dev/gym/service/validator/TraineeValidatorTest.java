package dev.gym.service.validator;

import dev.gym.service.dto.TraineeDtoRequest;
import dev.gym.service.validator.impl.TraineeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TraineeValidatorTest {

    private TraineeValidator traineeValidator;

    @BeforeEach
    void setUp() {
        traineeValidator = new TraineeValidator();
    }

    @Test
    void givenValidTraineeDtoRequest_whenValidate_thenSuccess() {
        TraineeDtoRequest traineeDtoRequest = new TraineeDtoRequest("firstName", "lastName", null, "username");
        traineeValidator.validate(traineeDtoRequest);
    }

    @Test
    void givenTraineeDtoRequest_whenFirstNameEmptyOrBlank_thenThrowIllegalArgumentException() {

        // firstName is empty
        TraineeDtoRequest dtoWithNullFirstName = new TraineeDtoRequest(null, "lastname", null, "");
        assertThrows(IllegalArgumentException.class,
                () -> traineeValidator.validate(dtoWithNullFirstName)
        );

        // firstName is blank
        TraineeDtoRequest dtoWithBlankFirstName = new TraineeDtoRequest("", "lastname", null, "");
        assertThrows(IllegalArgumentException.class,
                () -> traineeValidator.validate(dtoWithBlankFirstName)
        );
    }

    @Test
    void givenTraineeDtoRequest_whenLastNameEmptyOrBlank_thenThrowIllegalArgumentException() {

        // lastName is empty
        TraineeDtoRequest dtoWithEmptyLastName = new TraineeDtoRequest("firstname", null, null, "");
        assertThrows(IllegalArgumentException.class,
                () -> traineeValidator.validate(dtoWithEmptyLastName)
        );

        // lastName is blank
        TraineeDtoRequest dtoWithBlankLastName = new TraineeDtoRequest("firstname", "", null, "");
        assertThrows(IllegalArgumentException.class,
                () -> traineeValidator.validate(dtoWithBlankLastName)
        );
    }
}
