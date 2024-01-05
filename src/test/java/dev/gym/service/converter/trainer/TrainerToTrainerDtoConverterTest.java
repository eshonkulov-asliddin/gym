package dev.gym.service.converter.trainer;

import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.TrainingType;
import dev.gym.repository.model.enums.TrainingTypeEnum;
import dev.gym.service.dto.TrainerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerToTrainerDtoConverterTest {

    @InjectMocks
    private TrainerToTrainerDtoConverter trainerToTrainerDtoConverter;

    @Test
    void givenValidTrainer_whenConvert_thenReturnTrainerDto() {
        TrainingType trainingType = mock(TrainingType.class);
        when(trainingType.getTrainingTypeName()).thenReturn(TrainingTypeEnum.CARDIO);

        Trainee trainee = mock(Trainee.class);
        when(trainee.getUsername()).thenReturn("Jane.Doe");
        when(trainee.getFirstName()).thenReturn("Jane");
        when(trainee.getLastName()).thenReturn("Doe");
        when(trainee.getDateOfBirth()).thenReturn(LocalDate.of(2000, 1, 1));
        when(trainee.getAddress()).thenReturn("Address");
        when(trainee.isActive()).thenReturn(true);

        Trainer trainer = mock(Trainer.class);
        when(trainer.getUsername()).thenReturn("John.Doe");
        when(trainer.getFirstName()).thenReturn("John");
        when(trainer.getLastName()).thenReturn("Doe");
        when(trainer.getSpecialization()).thenReturn(trainingType);
        when(trainer.isActive()).thenReturn(true);
        when(trainer.getTrainees()).thenReturn(Set.of(trainee));

        TrainerDto trainerDto = trainerToTrainerDtoConverter.convert(trainer);

        assert trainerDto != null;
        assertEquals("John.Doe", trainerDto.username());
        assertEquals("John", trainerDto.firstName());
        assertEquals("Doe", trainerDto.lastName());
        assertEquals(TrainingTypeEnum.CARDIO, trainerDto.specialization().getTrainingTypeName());
        assertTrue(trainerDto.isActive());
        assertEquals(1, trainerDto.trainees().size());
    }
}
