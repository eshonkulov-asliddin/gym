package dev.gym.service.converter.trainer;

import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Training;
import dev.gym.repository.model.TrainingType;
import dev.gym.repository.model.enums.TrainingTypeEnum;
import dev.gym.service.dto.TrainerTrainingDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingToTrainerTrainingDtoTest {

    @InjectMocks
    private TrainingToTrainerTrainingDto trainingToTrainerTrainingDto;

    @Test
    void givenValidTraining_whenConvert_thenReturnTrainerTrainingDto() {
        TrainingType trainingType = mock(TrainingType.class);
        when(trainingType.getTrainingTypeName()).thenReturn(TrainingTypeEnum.CARDIO);

        Trainee trainee = mock(Trainee.class);
        when(trainee.getUsername()).thenReturn("Jane.Doe");

        Training training = mock(Training.class);
        when(training.getTrainingName()).thenReturn("Training");
        when(training.getTrainingDate()).thenReturn(LocalDate.of(2024, 1, 1));
        when(training.getTrainingType()).thenReturn(trainingType);
        when(training.getTrainingDuration()).thenReturn(60);
        when(training.getTrainee()).thenReturn(trainee);

        TrainerTrainingDto trainerTrainingDto = trainingToTrainerTrainingDto.convert(training);

        assertNotNull(trainerTrainingDto);
        assertEquals("Training", trainerTrainingDto.trainingName());
        assertEquals(LocalDate.of(2024, 1, 1), trainerTrainingDto.trainingDate());
        assertEquals(TrainingTypeEnum.CARDIO, trainerTrainingDto.trainingType());
        assertEquals(60, trainerTrainingDto.trainingDuration());
        assertEquals("Jane.Doe", trainerTrainingDto.traineeUsername());
    }
}
