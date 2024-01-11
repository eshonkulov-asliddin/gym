package dev.gym.service.impl;

import dev.gym.repository.TraineeRepository;
import dev.gym.repository.TrainerRepository;
import dev.gym.repository.TrainingRepository;
import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;
import dev.gym.service.dto.CreateTrainingDto;
import dev.gym.service.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Test
    void whenGetDtoClass_thenReturnCreateTrainingDto() {
        assertEquals(CreateTrainingDto.class, trainingService.getDtoClass());
    }

    @Test
    void whenAddTrainingWithInvalidTraineeUsername_thenThrowNotFoundException() {
        CreateTrainingDto createTrainingDto = new CreateTrainingDto("invalidTraineeUsername", "trainerUsername", "trainingName", LocalDate.of(2024, 1, 1), 60);
        when(traineeRepository.findByUsername("invalidTraineeUsername")).thenThrow(new NotFoundException("Trainee not found"));

        assertThrows(NotFoundException.class, () -> {
            trainingService.addTraining(createTrainingDto);
        });
    }

    @Test
    void whenAddTrainingWithInvalidTrainerUsername_thenThrowNotFoundException() {
        Trainee trainee = mock(Trainee.class);

        CreateTrainingDto createTrainingDto = new CreateTrainingDto("traineeUsername", "invalidTrainerUsername", "trainingName", LocalDate.of(2024, 1, 1), 60);
        when(traineeRepository.findByUsername("traineeUsername")).thenReturn(Optional.of(trainee));
        when(trainerRepository.findByUsername("invalidTrainerUsername")).thenThrow(new NotFoundException("Trainer not found"));

        assertThrows(NotFoundException.class, () -> {
            trainingService.addTraining(createTrainingDto);
        });
    }
}
