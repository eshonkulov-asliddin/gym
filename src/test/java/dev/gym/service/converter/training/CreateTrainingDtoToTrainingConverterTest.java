package dev.gym.service.converter.training;

import dev.gym.repository.model.Training;
import dev.gym.service.dto.CreateTrainingDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTrainingDtoToTrainingConverterTest {

    @InjectMocks
    private CreateTrainingDtoToTrainingConverter createTrainingDtoToTrainingConverter;

    @Test
    void givenValidCreateTrainingDto_whenConvert_thenReturnTraining() {
        CreateTrainingDto createTrainingDto = mock(CreateTrainingDto.class);
        when(createTrainingDto.trainingName()).thenReturn("Training");
        when(createTrainingDto.trainingDate()).thenReturn(LocalDate.of(2024, 1, 1));
        when(createTrainingDto.trainingDuration()).thenReturn(60);

        Training training = createTrainingDtoToTrainingConverter.convert(createTrainingDto);

        assert training != null;
        assertEquals("Training", training.getTrainingName());
        assertEquals(LocalDate.of(2024, 1, 1), training.getTrainingDate());
        assertEquals(60, training.getTrainingDuration());
    }
}
