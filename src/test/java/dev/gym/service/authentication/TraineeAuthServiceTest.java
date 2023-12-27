package dev.gym.service.authentication;

import dev.gym.repository.impl.UserRepositoryImpl;
import dev.gym.repository.model.Trainee;
import dev.gym.security.authentication.UserAuthService;
import dev.gym.service.exception.InvalidUsernameOrPasswordException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TraineeAuthServiceTest {

    static Trainee trainee;
    static UserAuthService traineeAuthService;
    static UserRepositoryImpl traineeRepositoryImpl;


    @BeforeAll
    static void setup() {
        traineeRepositoryImpl = mock(UserRepositoryImpl.class);
        trainee = mock(Trainee.class);
        traineeAuthService = new UserAuthService(traineeRepositoryImpl);

        when(trainee.getPassword()).thenReturn("password");
        when(traineeRepositoryImpl.findByUsername("trainerUsername")).thenReturn(Optional.of(trainee));
    }

    @Test
    void givenValidUsernameAndPassword_whenAuthenticate_thenSuccess() {
        traineeAuthService.authenticate("trainerUsername", "password");
    }

    @Test
    void givenInvalidPassword_whenAuthenticate_thenThrowInvalidUsernameOrPasswordException() {
        assertThrows(InvalidUsernameOrPasswordException.class,
                () -> traineeAuthService.authenticate("trainerUsername", "wrongPassword")
        );
    }

    @Test
    void givenInvalidUsername_whenAuthenticate_thenThrowInvalidUsernameOrPasswordException() {
        assertThrows(InvalidUsernameOrPasswordException.class,
                () -> traineeAuthService.authenticate("wrongUsername", "password")
        );
    }
}
