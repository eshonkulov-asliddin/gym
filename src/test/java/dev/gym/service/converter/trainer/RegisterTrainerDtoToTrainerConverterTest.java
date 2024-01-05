package dev.gym.service.converter.trainer;

import dev.gym.repository.model.Trainer;
import dev.gym.service.dto.RegisterTrainerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterTrainerDtoToTrainerConverterTest {

    @InjectMocks
    private RegisterTrainerDtoToTrainerConverter registerTrainerDtoToTrainerConverter;

    @Test
    void givenValidRegisterTrainerDto_whenConvert_thenReturnTrainer() {
        RegisterTrainerDto registerTrainerDto = mock(RegisterTrainerDto.class);
        when(registerTrainerDto.firstName()).thenReturn("John");
        when(registerTrainerDto.lastName()).thenReturn("Doe");

        Trainer trainer = registerTrainerDtoToTrainerConverter.convert(registerTrainerDto);

        assertNotNull(trainer);
        assertEquals("John", trainer.getFirstName());
        assertEquals("Doe", trainer.getLastName());
    }
}
