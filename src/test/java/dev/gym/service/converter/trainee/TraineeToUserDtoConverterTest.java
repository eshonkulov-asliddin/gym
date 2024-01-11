package dev.gym.service.converter.trainee;

import dev.gym.repository.model.Trainee;
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
class TraineeToUserDtoConverterTest {

    @InjectMocks
    private TraineeToUserDtoConverter traineeToUserDtoConverter;

    @Test
    void givenValidTrainee_whenConvert_thenReturnUserDto() {
        Trainee trainee = mock(Trainee.class);
        when(trainee.getUsername()).thenReturn("John.Doe");
        when(trainee.getPassword()).thenReturn("password");

        UserDto userDto = traineeToUserDtoConverter.convert(trainee);

        assertNotNull(userDto);
        assertEquals("John.Doe", userDto.username());
        assertEquals("password", userDto.password());
    }
}
