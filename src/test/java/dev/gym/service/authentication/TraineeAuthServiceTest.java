package dev.gym.service.authentication;

import dev.gym.repository.UserRepository;
import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.User;
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
    static UserRepository<User, Long> userRepository;


    @BeforeAll
    static void setup() {
        userRepository = mock(UserRepository.class);
        trainee = mock(Trainee.class);
        traineeAuthService = new UserAuthService(userRepository);

        when(trainee.getPassword()).thenReturn("password");
        when(userRepository.findByUsername("trainerUsername")).thenReturn(Optional.of(trainee));
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
