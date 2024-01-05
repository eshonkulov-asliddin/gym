package dev.gym.service.impl;

import dev.gym.repository.TraineeRepository;
import dev.gym.repository.TrainerRepository;
import dev.gym.repository.TrainingRepository;
import dev.gym.repository.datasource.credential.CredentialGenerator;
import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;
import dev.gym.service.dto.RegisterTraineeDto;
import dev.gym.service.dto.TraineeDto;
import dev.gym.service.dto.TraineeTrainerDto;
import dev.gym.service.dto.TraineeTrainingDto;
import dev.gym.service.dto.TrainerDto;
import dev.gym.service.dto.UpdateTraineeDto;
import dev.gym.service.dto.UserDto;
import dev.gym.service.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {

    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private CredentialGenerator credentialGenerator;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private TraineeServiceImpl traineeService;


    @Test
    void givenValidRequest_whenRegisterTrainee_ThenReturnUserDto() {
        RegisterTraineeDto registerTraineeDto = mock(RegisterTraineeDto.class);
        Trainee trainee = mock(Trainee.class);
        UserDto userDto = mock(UserDto.class);

        when(trainee.getFirstName()).thenReturn("firstName");
        when(trainee.getLastName()).thenReturn("lastName");

        when(userDto.username()).thenReturn("username");
        when(userDto.password()).thenReturn("password");

        when(credentialGenerator.generateUsername(trainee.getFirstName(), trainee.getLastName())).thenReturn("username");
        when(credentialGenerator.generatePassword()).thenReturn("password");

        when(conversionService.convert(registerTraineeDto, Trainee.class)).thenReturn(trainee);
        doNothing().when(traineeRepository).save(trainee);
        when(conversionService.convert(trainee, UserDto.class)).thenReturn(userDto);

        UserDto result = traineeService.register(registerTraineeDto);

        assertNotNull(result);
        assertEquals("username", result.username());
        assertEquals("password", result.password());
    }

    @Test
    void givenValidUsernameAndRequest_whenUpdate_thenReturnUpdatedTraineeDto() {
        Trainee newTrainee = mock(Trainee.class);
        Trainee oldTrainee = mock(Trainee.class);
        TraineeDto traineeDto = mock(TraineeDto.class);

        when(traineeDto.username()).thenReturn("username");

        doNothing().when(oldTrainee).update(newTrainee);

        UpdateTraineeDto updateTraineeDto = mock(UpdateTraineeDto.class);

        when(traineeRepository.findByUsername("username")).thenReturn(Optional.of(oldTrainee));
        when(conversionService.convert(updateTraineeDto, Trainee.class)).thenReturn(newTrainee);
        doNothing().when(traineeRepository).save(oldTrainee);
        when(conversionService.convert(newTrainee, TraineeDto.class)).thenReturn(traineeDto);

        TraineeDto updatedTraineeDto = traineeService.update("username", updateTraineeDto);

        assertNotNull(updatedTraineeDto);
        assertEquals("username", updatedTraineeDto.username());
    }

    @Test
    void givenInvalidUsernameAndRequest_whenUpdate_thenThrowNotFoundException() {
        UpdateTraineeDto updateTraineeDto = mock(UpdateTraineeDto.class);
        when(traineeRepository.findByUsername("username")).thenThrow(new NotFoundException("Trainee not found"));
        assertThrows(NotFoundException.class, () -> traineeService.update("username", updateTraineeDto));
    }

    @Test
    void givenValidTraineeUsernameAndTraineeTrainerDtoList_whenUpdateTraineeTrainers_thenReturnListOfTrainerDto() {
        Trainee trainee = mock(Trainee.class);
        Trainer trainer1 = new Trainer();
        Trainer trainer2 = new Trainer();
        TrainerDto trainerDto = mock(TrainerDto.class);
        TraineeTrainerDto traineeTrainerDto1 = mock(TraineeTrainerDto.class);
        TraineeTrainerDto traineeTrainerDto2 = mock(TraineeTrainerDto.class);
        List<TraineeTrainerDto> traineeTrainerDtoList = List.of(traineeTrainerDto1, traineeTrainerDto2);
        List<String> trainerUsernamesList = List.of("trainerUsername1", "trainerUsername2");

        when(traineeTrainerDto1.trainerUsername()).thenReturn("trainerUsername1");
        when(traineeTrainerDto2.trainerUsername()).thenReturn("trainerUsername2");
        when(traineeRepository.findByUsername("traineeUsername")).thenReturn(Optional.of(trainee));
        when(trainerRepository.findTrainersByUsernames(trainerUsernamesList)).thenReturn(List.of(trainer1, trainer2));
        doNothing().when(trainee).addTrainers(List.of(trainer1, trainer2));
        doNothing().when(traineeRepository).save(trainee);
        when(conversionService.convert(any(Trainer.class), eq(TrainerDto.class))).thenReturn(trainerDto);

        List<TrainerDto> trainerDtoList = traineeService.updateTrainers("traineeUsername", traineeTrainerDtoList);

        assert trainerDtoList != null;
        assertEquals(traineeTrainerDtoList.size(), trainerDtoList.size());
    }

    @Test
    void givenInvalidTraineeUsernameAndTraineeTrainerDtoList_whenUpdateTraineeTrainers_thenThrowNotFoundException() {
        TraineeTrainerDto traineeTrainerDto1 = mock(TraineeTrainerDto.class);
        TraineeTrainerDto traineeTrainerDto2 = mock(TraineeTrainerDto.class);
        List<TraineeTrainerDto> traineeTrainerDtoList = List.of(traineeTrainerDto1, traineeTrainerDto2);

        when(traineeRepository.findByUsername("traineeUsername")).thenThrow(new NotFoundException("Trainee not found"));

        assertThrows(NotFoundException.class, () -> traineeService.updateTrainers("traineeUsername", traineeTrainerDtoList));
    }

    @Test
    void givenValidTraineeUsernameAndInvalidTraineeTrainerDtoList_whenUpdateTraineeTrainers_thenReturnEmptyTrainerDtoList() {
        Trainee trainee = mock(Trainee.class);
        TraineeTrainerDto traineeTrainerDto1 = mock(TraineeTrainerDto.class);
        TraineeTrainerDto traineeTrainerDto2 = mock(TraineeTrainerDto.class);
        List<TraineeTrainerDto> traineeTrainerDtoList = List.of(traineeTrainerDto1, traineeTrainerDto2);
        List<String> trainerUsernamesList = List.of("trainerUsername1", "trainerUsername2");

        when(traineeTrainerDto1.trainerUsername()).thenReturn("trainerUsername1");
        when(traineeTrainerDto2.trainerUsername()).thenReturn("trainerUsername2");
        when(traineeRepository.findByUsername("traineeUsername")).thenReturn(Optional.of(trainee));
        when(trainerRepository.findTrainersByUsernames(trainerUsernamesList)).thenReturn(List.of());
        doNothing().when(trainee).addTrainers(List.of());
        doNothing().when(traineeRepository).save(trainee);

        List<TrainerDto> trainerDtoList = traineeService.updateTrainers("traineeUsername", traineeTrainerDtoList);

        assert trainerDtoList != null;
        assertEquals(0, trainerDtoList.size());
    }

    @Test
    void givenValidParameters_whenGetAllTraineeTrainings_thenReturnTraineeTrainingList() {
        Training training = new Training();
        TraineeTrainingDto traineeTrainingDto = mock(TraineeTrainingDto.class);

        when(traineeRepository.existByUsername("traineeUsername")).thenReturn(true);
        when(trainingRepository.findFor("traineeUsername", "trainerUsername", null, null)).thenReturn(List.of(training));
        when(conversionService.convert(any(Training.class), eq(TraineeTrainingDto.class))).thenReturn(traineeTrainingDto);

        List<TraineeTrainingDto> allTrainingsByUsername = traineeService.getAllTrainingsByUsername("traineeUsername",  null, null, "trainerUsername", null);

        assert allTrainingsByUsername != null;
        assertEquals(1, allTrainingsByUsername.size());
    }

    @Test
    void givenInvalidUsername_whenGetAllTraineeTrainings_thenThrowNotFoundException() {
        when(traineeRepository.existByUsername("traineeUsername")).thenThrow(new NotFoundException("Trainee not found"));
        assertThrows(NotFoundException.class, () -> traineeService.getAllTrainingsByUsername("traineeUsername", null, null, null, null));
    }

    @Test
    void givenValidId_whenDelete_thenSuccess() {
        when(traineeRepository.existById(1L)).thenReturn(true);
        when(traineeRepository.delete(1L)).thenReturn(true);

        assertTrue(traineeService.deleteById(1L));
    }

    @Test
    void givenInvalidId_whenDelete_thenFalse() {
        when(traineeRepository.existById(1L)).thenReturn(false);
        assertFalse(traineeService.deleteById(1L));
    }

    @Test
    void whenGetDtoClass_thenReturnTraineeDto() {
        assertEquals(TraineeDto.class, traineeService.getDtoClass());
    }
}
