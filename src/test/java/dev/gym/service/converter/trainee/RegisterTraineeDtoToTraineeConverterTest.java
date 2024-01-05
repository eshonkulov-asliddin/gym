package dev.gym.service.converter.trainee;

import dev.gym.repository.model.Trainee;
import dev.gym.service.dto.RegisterTraineeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterTraineeDtoToTraineeConverterTest {

    @InjectMocks
    private RegisterTraineeDtoToTraineeConverter registerTraineeDtoToTraineeConverter;

    @Test
    void givenValidRegisterTraineeDto_whenConvert_thenReturnTrainee() {
        RegisterTraineeDto registerTraineeDto = mock(RegisterTraineeDto.class);

        when(registerTraineeDto.firstName()).thenReturn("John");
        when(registerTraineeDto.lastName()).thenReturn("Doe");
        when(registerTraineeDto.dateOfBirth()).thenReturn(LocalDate.of(1990, 1, 1));
        when(registerTraineeDto.address()).thenReturn("123 Main St.");

        Trainee trainee = registerTraineeDtoToTraineeConverter.convert(registerTraineeDto);

        assertNotNull(trainee);
        assertEquals("John", trainee.getFirstName());
        assertEquals("Doe", trainee.getLastName());
        assertEquals(LocalDate.of(1990, 1, 1), trainee.getDateOfBirth());
        assertEquals("123 Main St.", trainee.getAddress());
        assertTrue(trainee.isActive());
    }
}
