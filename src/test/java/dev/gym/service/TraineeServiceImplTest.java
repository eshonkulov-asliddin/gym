package dev.gym.service;

import dev.gym.repository.TraineeRepository;
import dev.gym.repository.TrainerRepository;
import dev.gym.repository.impl.TraineeRepositoryImpl;
import dev.gym.repository.impl.TrainerRepositoryImpl;
import dev.gym.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TraineeServiceImplTest {

    @Test
    void givenValidId_whenDelete_thenSuccess() {

        TraineeRepository traineeRepository = mock(TraineeRepositoryImpl.class);
        TrainerRepository trainerRepository = mock(TrainerRepositoryImpl.class);
        ConversionService conversionService = mock(ConversionService.class);

        when(traineeRepository.existById(1L)).thenReturn(true);
        when(traineeRepository.delete(1L)).thenReturn(true);

        TraineeServiceImpl traineeServiceImpl = new TraineeServiceImpl(traineeRepository, conversionService, trainerRepository);

        assertTrue(traineeServiceImpl.deleteById(1L));

    }

    @Test
    void givenInvalidId_whenDelete_thenFalse() {

        TraineeRepository traineeRepository = mock(TraineeRepositoryImpl.class);
        TrainerRepository trainerRepository = mock(TrainerRepositoryImpl.class);
        ConversionService conversionService = mock(ConversionService.class);

        when(traineeRepository.existById(1L)).thenReturn(false);

        TraineeServiceImpl traineeServiceImpl = new TraineeServiceImpl(traineeRepository, conversionService, trainerRepository);

        assertFalse(traineeServiceImpl.deleteById(1L));
    }
}
