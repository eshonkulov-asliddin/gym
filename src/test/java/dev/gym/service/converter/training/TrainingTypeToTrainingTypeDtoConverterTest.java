package dev.gym.service.converter.training;

import dev.gym.repository.model.TrainingType;
import dev.gym.repository.model.enums.TrainingTypeEnum;
import dev.gym.service.dto.TrainingTypeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingTypeToTrainingTypeDtoConverterTest {

    @InjectMocks
    private TrainingTypeToTrainingTypeDtoConverter trainingTypeToTrainingTypeDtoConverter;

    @Test
    void givenTrainingType_whenConvert_thenReturnTrainingTypeDto() {
        TrainingType trainingType = mock(TrainingType.class);
        when(trainingType.getId()).thenReturn(1L);
        when(trainingType.getTrainingTypeName()).thenReturn(TrainingTypeEnum.CARDIO);

        TrainingTypeDto trainingTypeDto = trainingTypeToTrainingTypeDtoConverter.convert(trainingType);

        assert trainingTypeDto != null;
        assertEquals(trainingTypeDto.trainingType(), TrainingTypeEnum.CARDIO.toString());
    }
}
