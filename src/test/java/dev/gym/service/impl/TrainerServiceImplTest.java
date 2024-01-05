package dev.gym.service.impl;

import dev.gym.repository.TrainerRepository;
import dev.gym.repository.TrainingRepository;
import dev.gym.repository.TrainingTypeRepository;
import dev.gym.repository.datasource.credential.CredentialGenerator;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;
import dev.gym.repository.model.TrainingType;
import dev.gym.service.dto.RegisterTrainerDto;
import dev.gym.service.dto.TrainerDto;
import dev.gym.service.dto.TrainerTrainingDto;
import dev.gym.service.dto.UpdateTrainerDto;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {

    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TrainingTypeRepository trainingTypeRepository;
    @Mock
    private CredentialGenerator credentialGenerator;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Test
    void givenValidRegisterTrainerDto_whenRegister_thenReturnUserDto() {
        RegisterTrainerDto registerTrainerDto = mock(RegisterTrainerDto.class);
        UserDto userDto = mock(UserDto.class);
        Trainer trainer = mock(Trainer.class);
        TrainingType trainingType = mock(TrainingType.class);

        when(registerTrainerDto.specialization()).thenReturn("STRENGTH");

        when(userDto.username()).thenReturn("username");
        when(userDto.password()).thenReturn("password");

        when(trainer.getFirstName()).thenReturn("firstName");
        when(trainer.getLastName()).thenReturn("lastName");

        when(conversionService.convert(registerTrainerDto, Trainer.class)).thenReturn(trainer);
        when(credentialGenerator.generateUsername("firstName", "lastName")).thenReturn("username");
        when(credentialGenerator.generatePassword()).thenReturn("password");
        when(trainingTypeRepository.findByType(any())).thenReturn(trainingType);
        doNothing().when(trainerRepository).save(trainer);
        when(conversionService.convert(trainer, UserDto.class)).thenReturn(userDto);

        UserDto registeredUserDto = trainerService.register(registerTrainerDto);

        assertNotNull(registeredUserDto);
        assertEquals("username", registeredUserDto.username());
        assertEquals("password", registeredUserDto.password());
    }

    @Test
    void givenValidTrainerUsernameAndUpdateTrainerDtoRequest_whenUpdate_thenReturnTrainerDto() {
        UpdateTrainerDto updateTrainerDto = mock(UpdateTrainerDto.class);
        Trainer oldTrainer = mock(Trainer.class);
        Trainer newTrainer = mock(Trainer.class);
        TrainerDto trainerDto = mock(TrainerDto.class);
        TrainingType trainingType = mock(TrainingType.class);

        when(updateTrainerDto.specialization()).thenReturn("STRENGTH");

        when(trainerRepository.findByUsername("trainerUsername")).thenReturn(Optional.of(oldTrainer));
        when(conversionService.convert(updateTrainerDto, Trainer.class)).thenReturn(newTrainer);
        when(trainingTypeRepository.findByType(any())).thenReturn(trainingType);
        doNothing().when(trainerRepository).save(oldTrainer);
        when(conversionService.convert(newTrainer, TrainerDto.class)).thenReturn(trainerDto);
        when(trainerDto.username()).thenReturn("trainerUsername");

        TrainerDto registeredTrainerDto = trainerService.update("trainerUsername", updateTrainerDto);

        assertNotNull(registeredTrainerDto);
        assertEquals("trainerUsername", registeredTrainerDto.username());
    }

    @Test
    void givenInvalidUsernameAndValidUpdateTrainerDtoRequest_whenUpdate_thenThrowNotFoundException() {
        UpdateTrainerDto updateTrainerDto = mock(UpdateTrainerDto.class);

        when(updateTrainerDto.specialization()).thenReturn("STRENGTH");
        when(trainerRepository.findByUsername("trainerUsername")).thenThrow(new NotFoundException("Trainer not found"));

        assertThrows(NotFoundException.class, () -> trainerService.update("trainerUsername", updateTrainerDto));
    }

    @Test
    void givenValidParameters_whenGetAllTraineeTrainings_thenReturnTrainerTrainingList() {
        Training training = new Training();
        TrainerTrainingDto trainerTrainingDto = mock(TrainerTrainingDto.class);

        when(trainerRepository.existByUsername("trainerUsername")).thenReturn(true);
        when(trainingRepository.findFor("traineeUsername", "trainerUsername", null, null)).thenReturn(List.of(training));
        when(conversionService.convert(any(Training.class), eq(TrainerTrainingDto.class))).thenReturn(trainerTrainingDto);

        List<TrainerTrainingDto> allTrainingsByUsername = trainerService.getAllTrainingsByUsername("trainerUsername",  null, null, "traineeUsername");

        assert allTrainingsByUsername != null;
        assertEquals(1, allTrainingsByUsername.size());
    }

    @Test
    void givenInvalidUsername_whenGetAllTraineeTrainings_thenThrowNotFoundException() {
        when(trainerRepository.existByUsername("trainerUsername")).thenThrow(new NotFoundException("Trainer not found"));
        assertThrows(NotFoundException.class, () -> trainerService.getAllTrainingsByUsername("trainerUsername", null, null, null));
    }

    @Test
    void givenRequest_whenGetAllActiveUnAssignedTrainers_thenReturnTrainerDtoList() {
        Trainer trainer = mock(Trainer.class);
        TrainerDto trainerDto = mock(TrainerDto.class);

        when(trainerRepository.findActiveUnAssignedTrainers()).thenReturn(List.of(trainer));
        when(conversionService.convert(any(Trainer.class), eq(TrainerDto.class))).thenReturn(trainerDto);

        List<TrainerDto> allActiveUnAssignedTrainers = trainerService.getAllActiveUnAssignedTrainers();

        assert allActiveUnAssignedTrainers != null;
        assertEquals(1, allActiveUnAssignedTrainers.size());
    }

    @Test
    void whenGetDtoClass_thenReturnTrainerDto() {
        assertEquals(TrainerDto.class, trainerService.getDtoClass());
    }
}
