package dev.gym.service.converter.trainer;

import dev.gym.repository.model.Trainer;
import dev.gym.service.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerToUserDtoConverterTest {

    @InjectMocks
    private TrainerToUserDtoConverter trainerToUserDtoConverter;

    @Test
    void givenValidTrainer_whenConvert_thenReturnUserDto() {
        Trainer trainer = mock(Trainer.class);
        when(trainer.getUsername()).thenReturn("John.Doe");
        when(trainer.getPassword()).thenReturn("password");

        UserDto userDto = trainerToUserDtoConverter.convert(trainer);

        assertNotNull(userDto);
        assertEquals("John.Doe", userDto.username());
        assertEquals("password", userDto.password());
    }
}
