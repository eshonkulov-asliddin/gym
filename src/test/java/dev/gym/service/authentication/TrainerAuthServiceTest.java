package dev.gym.service.authentication;

import dev.gym.model.Trainer;
import dev.gym.repository.impl.TrainerRepository;
import dev.gym.service.exception.InvalidUsernameOrPasswordException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainerAuthServiceTest {

    static TrainerRepository trainerRepository;
    static Trainer trainer;
    static TrainerAuthService trainerAuthService;

    @BeforeAll
    static void setup() {
        trainerRepository = mock(TrainerRepository.class);
        trainer = mock(Trainer.class);
        trainerAuthService = new TrainerAuthService(trainerRepository);

        when(trainer.getPassword()).thenReturn("password");
        when(trainerRepository.findByUsername("username")).thenReturn(Optional.of(trainer));
    }

    @Test
    void givenValidUsernameAndPassword_whenAuthenticate_thenSuccess() {
        trainerAuthService.authenticate("username", "password");
    }

    @Test
    void givenInvalidPassword_whenAuthenticate_thenThrowInvalidUsernameOrPasswordException() {
        assertThrows(InvalidUsernameOrPasswordException.class,
                () -> trainerAuthService.authenticate("username", "wrongPassword")
        );
    }

    @Test
    void givenInvalidUsername_whenAuthenticate_thenThrowInvalidUsernameOrPasswordException() {
        assertThrows(InvalidUsernameOrPasswordException.class,
                () -> trainerAuthService.authenticate("wrongUsername", "password")
        );
    }

}
