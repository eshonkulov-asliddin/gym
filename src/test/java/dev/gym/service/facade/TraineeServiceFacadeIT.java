package dev.gym.service.facade;

import dev.gym.config.AppConfig;
import dev.gym.model.Trainee;
import dev.gym.model.Training;
import dev.gym.service.dto.TraineeDtoRequest;
import dev.gym.service.dto.TraineeDtoResponse;
import dev.gym.service.exception.InvalidUsernameOrPasswordException;
import dev.gym.service.facade.impl.AbstractUserFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(classes = {AppConfig.class})
class TraineeServiceFacadeIT {

    @Autowired
    protected AbstractUserFacade<TraineeDtoRequest, TraineeDtoResponse, Long, Trainee, Training> traineeServiceFacade;

    @Test
    void givenWrongUsernameAndPassword_whenGetAll_thenThrowInvalidUsernameOrPasswordException() {
        String USERNAME = "wrongUsername";
        String PASSWORD = "wrongPassword";

        assertThrows(InvalidUsernameOrPasswordException.class,
                () -> traineeServiceFacade.getAll(USERNAME, PASSWORD)
        );
    }

}
