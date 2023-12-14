package dev.gym.service;

import dev.gym.repository.impl.TraineeRepository;
import dev.gym.service.dto.TraineeDtoRequest;
import dev.gym.service.impl.TraineeService;
import dev.gym.service.mapper.TraineeMapper;
import dev.gym.service.validator.impl.TraineeValidator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TraineeServiceTest {

    @Test
    void givenInvalidTraineeDtoRequest_whenCreate_thenThrowException() {

        TraineeRepository traineeRepository = mock(TraineeRepository.class);
        TraineeMapper traineeMapper = mock(TraineeMapper.class);

        TraineeValidator traineeValidator = new TraineeValidator();
        TraineeDtoRequest traineeDtoRequest = new TraineeDtoRequest("", "", LocalDate.of(2000, 1, 1), "");

        TraineeService traineeService = new TraineeService(traineeRepository, traineeValidator, traineeMapper);

        assertThrows(IllegalArgumentException.class,
                () -> traineeService.save(traineeDtoRequest)
        );

    }

    @Test
    void givenValidId_whenDelete_thenSuccess() {

        TraineeRepository traineeRepository = mock(TraineeRepository.class);
        TraineeMapper traineeMapper = mock(TraineeMapper.class);
        TraineeValidator traineeValidator = mock(TraineeValidator.class);

        when(traineeRepository.existById(1L)).thenReturn(true);
        when(traineeRepository.delete(1L)).thenReturn(true);

        TraineeService traineeService = new TraineeService(traineeRepository, traineeValidator, traineeMapper);

        assertTrue(traineeService.deleteById(1L));

    }

    @Test
    void givenInvalidId_whenDelete_thenFalse() {

        TraineeRepository traineeRepository = mock(TraineeRepository.class);
        TraineeMapper traineeMapper = mock(TraineeMapper.class);
        TraineeValidator traineeValidator = mock(TraineeValidator.class);

        when(traineeRepository.existById(1L)).thenReturn(false);

        TraineeService traineeService = new TraineeService(traineeRepository, traineeValidator, traineeMapper);

        assertFalse(traineeService.deleteById(1L));
    }
}
