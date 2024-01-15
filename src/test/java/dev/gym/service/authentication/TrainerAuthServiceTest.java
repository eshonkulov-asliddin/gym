package dev.gym.service.authentication;

import dev.gym.repository.UserRepository;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.User;
import dev.gym.security.authentication.UserAuthService;
import dev.gym.service.exception.InvalidUsernameOrPasswordException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainerAuthServiceTest {

    static UserRepository<User, Long> trainerRepositoryImpl;
    static Trainer trainer;
    static UserAuthService trainerAuthService;

    @BeforeAll
    static void setup() {
        trainerRepositoryImpl = mock(UserRepository.class);
        trainer = mock(Trainer.class);
        trainerAuthService = new UserAuthService(trainerRepositoryImpl);

        when(trainer.getPassword()).thenReturn("password");
        when(trainerRepositoryImpl.findByUsername("trainerUsername")).thenReturn(Optional.of(trainer));
    }

    @Test
    void givenValidUsernameAndPassword_whenAuthenticate_thenSuccess() {
        trainerAuthService.authenticate("trainerUsername", "password");
    }

    @Test
    void givenInvalidPassword_whenAuthenticate_thenThrowInvalidUsernameOrPasswordException() {
        assertThrows(InvalidUsernameOrPasswordException.class,
                () -> trainerAuthService.authenticate("trainerUsername", "wrongPassword")
        );
    }

    @Test
    void givenInvalidUsername_whenAuthenticate_thenThrowInvalidUsernameOrPasswordException() {
        assertThrows(InvalidUsernameOrPasswordException.class,
                () -> trainerAuthService.authenticate("wrongUsername", "password")
        );
    }

}
