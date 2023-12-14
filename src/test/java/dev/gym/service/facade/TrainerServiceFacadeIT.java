package dev.gym.service.facade;

import dev.gym.config.AppConfig;
import dev.gym.model.Trainer;
import dev.gym.model.Training;
import dev.gym.service.dto.TrainerDtoRequest;
import dev.gym.service.dto.TrainerDtoResponse;
import dev.gym.service.exception.InvalidUsernameOrPasswordException;
import dev.gym.service.facade.impl.AbstractUserFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(classes = {AppConfig.class})
class TrainerServiceFacadeIT {

    @Autowired
    protected AbstractUserFacade<TrainerDtoRequest, TrainerDtoResponse, Long, Trainer, Training> trainerServiceFacade;

    @Test
    void givenWrongUsernameAndPassword_whenDelete_thenThrowInvalidUsernameOrPasswordException() {
        String USERNAME = "wrongUsername";
        String PASSWORD = "wrongPassword";

        assertThrows(InvalidUsernameOrPasswordException.class,
                () -> trainerServiceFacade.deleteByUsername(USERNAME, PASSWORD)
        );
    }

}
