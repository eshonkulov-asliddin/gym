package dev.gym.service.converter.trainee;

import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Trainer;
import dev.gym.service.dto.TraineeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeToTraineeDtoConverterTest {

    @InjectMocks
    private TraineeToTraineeDtoConverter traineeToTraineeDtoConverter;

    @Test
    void givenValidTrainee_whenConvert_thenReturnTraineeDto() {
        Trainer trainer = mock(Trainer.class);
        Trainee trainee = mock(Trainee.class);

        when(trainer.getUsername()).thenReturn("Tom.Jerry");
        when(trainer.getFirstName()).thenReturn("Tom");
        when(trainer.getLastName()).thenReturn("Jerry");
        when(trainer.isActive()).thenReturn(true);

        when(trainee.getUsername()).thenReturn("John.Doe");
        when(trainee.getFirstName()).thenReturn("John");
        when(trainee.getLastName()).thenReturn("Doe");
        when(trainee.getDateOfBirth()).thenReturn(LocalDate.of(1990, 1, 1));
        when(trainee.getAddress()).thenReturn("123 Main St.");
        when(trainee.isActive()).thenReturn(true);
        when(trainee.getTrainers()).thenReturn(Set.of(trainer));

        TraineeDto traineeDto = traineeToTraineeDtoConverter.convert(trainee);

        assertNotNull(traineeDto);
        assertEquals("John.Doe", traineeDto.username());
        assertEquals("John", traineeDto.firstName());
        assertEquals("Doe", traineeDto.lastName());
        assertEquals(LocalDate.of(1990, 1, 1), traineeDto.dateOfBirth());
        assertEquals("123 Main St.", traineeDto.address());
        assertTrue(traineeDto.isActive());

        assertFalse(traineeDto.trainers().isEmpty());
        assertEquals("Tom.Jerry", traineeDto.trainers().get(0).username());
        assertEquals("Tom", traineeDto.trainers().get(0).firstName());
        assertEquals("Jerry", traineeDto.trainers().get(0).lastName());
        assertTrue(traineeDto.trainers().get(0).isActive());
        assertNull(traineeDto.trainers().get(0).trainees()); // set trainee to null to avoid infinite recursion
    }
}
