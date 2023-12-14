package dev.gym.service.authentication;

import dev.gym.model.Trainee;
import dev.gym.repository.impl.TraineeRepository;
import dev.gym.service.exception.InvalidUsernameOrPasswordException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TraineeAuthServiceTest {

    static TraineeRepository traineeRepository;
    static Trainee trainee;
    static TraineeAuthService traineeAuthService;


    @BeforeAll
    static void setup() {
        traineeRepository = mock(TraineeRepository.class);
        trainee = mock(Trainee.class);
        traineeAuthService = new TraineeAuthService(traineeRepository);

        when(trainee.getPassword()).thenReturn("password");
        when(traineeRepository.findByUsername("username")).thenReturn(Optional.of(trainee));
    }

    @Test
    void givenValidUsernameAndPassword_whenAuthenticate_thenSuccess() {
        traineeAuthService.authenticate("username", "password");
    }

    @Test
    void givenInvalidPassword_whenAuthenticate_thenThrowInvalidUsernameOrPasswordException() {
        assertThrows(InvalidUsernameOrPasswordException.class,
                () -> traineeAuthService.authenticate("username", "wrongPassword")
        );
    }

    @Test
    void givenInvalidUsername_whenAuthenticate_thenThrowInvalidUsernameOrPasswordException() {
        assertThrows(InvalidUsernameOrPasswordException.class,
                () -> traineeAuthService.authenticate("wrongUsername", "password")
        );
    }
}
