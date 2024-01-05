package dev.gym.service.converter.trainee;

import dev.gym.repository.model.Trainee;
import dev.gym.service.dto.UpdateTraineeDto;
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
class UpdateTraineeDtoToTraineeConverterTest {

    @InjectMocks
    private UpdateTraineeDtoToTraineeConverter updateTraineeDtoToTraineeConverter;

    @Test
    void givenValidUpdateTraineeDto_whenConvert_thenReturnTrainee() {
        UpdateTraineeDto updateTraineeDto = mock(UpdateTraineeDto.class);
        when(updateTraineeDto.firstName()).thenReturn("John");
        when(updateTraineeDto.lastName()).thenReturn("Doe");
        when(updateTraineeDto.dateOfBirth()).thenReturn(LocalDate.of(1990, 1, 1));
        when(updateTraineeDto.address()).thenReturn("123 Main St.");
        when(updateTraineeDto.isActive()).thenReturn(true);

        Trainee trainee = updateTraineeDtoToTraineeConverter.convert(updateTraineeDto);

        assertNotNull(trainee);
        assertEquals("John", trainee.getFirstName());
        assertEquals("Doe", trainee.getLastName());
        assertEquals(LocalDate.of(1990, 1, 1), trainee.getDateOfBirth());
        assertEquals("123 Main St.", trainee.getAddress());
        assertTrue(trainee.isActive());
    }
}
