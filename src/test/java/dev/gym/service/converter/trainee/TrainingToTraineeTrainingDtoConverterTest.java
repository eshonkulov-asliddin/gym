package dev.gym.service.converter.trainee;

import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;
import dev.gym.repository.model.TrainingType;
import dev.gym.repository.model.enums.TrainingTypeEnum;
import dev.gym.service.dto.TraineeTrainingDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingToTraineeTrainingDtoConverterTest {

    @InjectMocks
    private TrainingToTraineeTrainingDto trainingToTraineeTrainingDto;

    @Test
    void givenValidTraining_whenConvert_thenReturnTraineeTrainingDto() {
        TrainingType trainingType = mock(TrainingType.class);
        when(trainingType.getTrainingTypeName()).thenReturn(TrainingTypeEnum.CARDIO);

        Trainer trainer = mock(Trainer.class);
        when(trainer.getUsername()).thenReturn("Tom.Jerry");

        Training training = mock(Training.class);
        when(training.getTrainingName()).thenReturn("Test Training");
        when(training.getTrainingDate()).thenReturn(LocalDate.of(2024, 1, 1));
        when(training.getTrainingType()).thenReturn(trainingType);
        when(training.getTrainingDuration()).thenReturn(60);
        when(training.getTrainer()).thenReturn(trainer);

        TraineeTrainingDto traineeTrainingDto = trainingToTraineeTrainingDto.convert(training);

        assert traineeTrainingDto != null;
        assertEquals("Test Training", traineeTrainingDto.trainingName());
        assertEquals(LocalDate.of(2024, 1, 1), traineeTrainingDto.trainingDate());
        assertEquals(TrainingTypeEnum.CARDIO, traineeTrainingDto.trainingType());
        assertEquals(60, traineeTrainingDto.trainingDuration());
        assertEquals("Tom.Jerry", traineeTrainingDto.trainerUsername());
    }
}
