package dev.gym.service.converter.trainer;

import dev.gym.repository.TrainingTypeRepository;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.TrainingType;
import dev.gym.repository.model.enums.TrainingTypeEnum;
import dev.gym.service.dto.UpdateTrainerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateTrainerDtoRequestToTrainerConverterTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepository;
    @InjectMocks
    private UpdateTrainerDtoRequestToTrainerConverter updateTrainerDtoRequestToTrainerConverter;

    @Test
    void givenValidUpdateTrainerDtoRequest_whenConvert_thenReturnTrainer() {
        TrainingType trainingType = mock(TrainingType.class);
        when(trainingType.getTrainingTypeName()).thenReturn(TrainingTypeEnum.CARDIO);

        when(trainingTypeRepository.findByName("CARDIO")).thenReturn(trainingType);

        UpdateTrainerDto updateTrainerDto = mock(UpdateTrainerDto.class);
        when(updateTrainerDto.firstName()).thenReturn("John");
        when(updateTrainerDto.lastName()).thenReturn("Doe");
        when(updateTrainerDto.specialization()).thenReturn("CARDIO");
        when(updateTrainerDto.isActive()).thenReturn(true);

        Trainer trainer = updateTrainerDtoRequestToTrainerConverter.convert(updateTrainerDto);

        assert trainer != null;
        assertEquals("John", trainer.getFirstName());
        assertEquals("Doe", trainer.getLastName());
        assertEquals("CARDIO", trainer.getSpecialization().getTrainingTypeName().toString());
        assertTrue(trainer.isActive());
    }
}
