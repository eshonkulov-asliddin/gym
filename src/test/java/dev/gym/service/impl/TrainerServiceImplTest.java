package dev.gym.service.impl;

import dev.gym.repository.TrainerRepository;
import dev.gym.repository.TrainingRepository;
import dev.gym.repository.TrainingTypeRepository;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;
import dev.gym.repository.model.TrainingType;
import dev.gym.security.credential.CredentialGenerator;
import dev.gym.security.jwt.JwtUtil;
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
    @Mock
    private JwtUtil jwtUtil;
    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Test
    void givenValidRegisterTrainerDto_whenRegister_thenReturnUserDto() {
        RegisterTrainerDto registerTrainerDto = mock(RegisterTrainerDto.class);
        Trainer trainer = mock(Trainer.class);
        TrainingType trainingType = mock(TrainingType.class);

        when(registerTrainerDto.specialization()).thenReturn("STRENGTH");

        when(trainer.getFirstName()).thenReturn("firstName");
        when(trainer.getLastName()).thenReturn("lastName");
        when(trainer.getUsername()).thenReturn("username");

        when(conversionService.convert(registerTrainerDto, Trainer.class)).thenReturn(trainer);
        when(credentialGenerator.generateUsername("firstName", "lastName")).thenReturn("username");
        when(credentialGenerator.generatePassword()).thenReturn("password");
        when(jwtUtil.generateToken("username")).thenReturn("token");
        when(trainingTypeRepository.findByTrainingTypeName(any())).thenReturn(trainingType);
        when(trainerRepository.save(trainer)).thenReturn(trainer);

        UserDto registeredUserDto = trainerService.register(registerTrainerDto);

        assertNotNull(registeredUserDto);
        assertEquals("username", registeredUserDto.username());
        assertEquals("token", registeredUserDto.token());
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
        when(trainingTypeRepository.findByTrainingTypeName(any())).thenReturn(trainingType);
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

        when(trainerRepository.existsByUsername("trainerUsername")).thenReturn(true);
        when(trainingRepository.findFor("traineeUsername", "trainerUsername", null, null)).thenReturn(List.of(training));
        when(conversionService.convert(any(Training.class), eq(TrainerTrainingDto.class))).thenReturn(trainerTrainingDto);

        List<TrainerTrainingDto> allTrainingsByUsername = trainerService.getAllTrainingsByUsername("trainerUsername",  null, null, "traineeUsername");

        assert allTrainingsByUsername != null;
        assertEquals(1, allTrainingsByUsername.size());
    }

    @Test
    void givenInvalidUsername_whenGetAllTraineeTrainings_thenThrowNotFoundException() {
        when(trainerRepository.existsByUsername("trainerUsername")).thenThrow(new NotFoundException("Trainer not found"));
        assertThrows(NotFoundException.class, () -> trainerService.getAllTrainingsByUsername("trainerUsername", null, null, null));
    }

    @Test
    void givenRequest_whenGetAllActiveUnAssignedTrainers_thenReturnTrainerDtoList() {
        Trainer trainer = mock(Trainer.class);
        TrainerDto trainerDto = mock(TrainerDto.class);

        when(trainerRepository.findByIsActiveTrueAndAssignedUsersIsEmpty()).thenReturn(List.of(trainer));
        when(conversionService.convert(any(Trainer.class), eq(TrainerDto.class))).thenReturn(trainerDto);

        List<TrainerDto> allActiveUnAssignedTrainers = trainerService.getByIsActiveTrueAndAssignedTraineesIsEmpty();

        assert allActiveUnAssignedTrainers != null;
        assertEquals(1, allActiveUnAssignedTrainers.size());
    }

    @Test
    void whenGetDtoClass_thenReturnTrainerDto() {
        assertEquals(TrainerDto.class, trainerService.getDtoClass());
    }
}
