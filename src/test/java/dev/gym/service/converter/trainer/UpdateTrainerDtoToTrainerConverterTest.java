package dev.gym.service.converter.trainer;

import dev.gym.repository.model.Trainer;
import dev.gym.service.dto.UpdateTrainerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateTrainerDtoToTrainerConverterTest {

    @InjectMocks
    private UpdateTrainerDtoToTrainerConverter updateTrainerDtoToTrainerConverter;

    @Test
    void givenValidUpdateTrainerDtoRequest_whenConvert_thenReturnTrainer() {
        UpdateTrainerDto updateTrainerDto = mock(UpdateTrainerDto.class);
        when(updateTrainerDto.firstName()).thenReturn("John");
        when(updateTrainerDto.lastName()).thenReturn("Doe");
        when(updateTrainerDto.isActive()).thenReturn(true);

        Trainer trainer = updateTrainerDtoToTrainerConverter.convert(updateTrainerDto);

        assertNotNull(trainer);
        assertEquals("John", trainer.getFirstName());
        assertEquals("Doe", trainer.getLastName());
        assertTrue(trainer.isActive());
    }
}
