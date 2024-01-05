package dev.gym.service.converter.trainer;

import dev.gym.repository.TrainingTypeRepository;
import dev.gym.repository.datasource.credential.CredentialGenerator;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.TrainingType;
import dev.gym.repository.model.enums.TrainingTypeEnum;
import dev.gym.service.dto.RegisterTrainerDto;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterTrainerDtoRequestToTrainerConverterTest {

    @Mock
    private CredentialGenerator credentialGenerator;
    @Mock
    private TrainingTypeRepository trainingTypeRepository;
    @InjectMocks
    private RegisterTrainerDtoRequestToTrainerConverter registerTrainerDtoRequestToTrainerConverter;

    @Test
    void givenValidRegisterTrainerDto_whenConvert_thenReturnTrainer() {
        RegisterTrainerDto registerTrainerDto = mock(RegisterTrainerDto.class);
        when(registerTrainerDto.firstName()).thenReturn("John");
        when(registerTrainerDto.lastName()).thenReturn("Doe");
        when(registerTrainerDto.specialization()).thenReturn(TrainingTypeEnum.CARDIO.toString());
        when(credentialGenerator.generateUsername("John", "Doe")).thenReturn("John.Doe");
        when(credentialGenerator.generatePassword()).thenReturn("password");

        when(trainingTypeRepository.findByName("CARDIO")).thenReturn(mock(TrainingType.class));

        Trainer trainer = registerTrainerDtoRequestToTrainerConverter.convert(registerTrainerDto);

        assert trainer != null;
        assertEquals("John", trainer.getFirstName());
        assertEquals("Doe", trainer.getLastName());
        assertEquals("John.Doe", trainer.getUsername());
        assertEquals("password", trainer.getPassword());
        assertNotNull(trainer.getSpecialization());

    }

    @Test
    void givenInvalidTrainingType_whenConvert_thenThrowException() {
        RegisterTrainerDto registerTrainerDto = mock(RegisterTrainerDto.class);
        when(registerTrainerDto.firstName()).thenReturn("John");
        when(registerTrainerDto.lastName()).thenReturn("Doe");
        when(registerTrainerDto.specialization()).thenReturn(TrainingTypeEnum.CARDIO.toString());
        when(credentialGenerator.generateUsername("John", "Doe")).thenReturn("John.Doe");
        when(credentialGenerator.generatePassword()).thenReturn("password");

        when(trainingTypeRepository.findByName("CARDIO")).thenThrow(NoResultException.class);

        assertThrows(NoResultException.class, () -> registerTrainerDtoRequestToTrainerConverter.convert(registerTrainerDto));
    }
}
