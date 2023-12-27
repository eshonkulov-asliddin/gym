package dev.gym.service.facade;

import dev.gym.repository.config.RepositoryConfig;
import dev.gym.security.config.SecurityConfig;
import dev.gym.service.config.ServiceConfig;
import dev.gym.service.exception.InvalidUsernameOrPasswordException;
import dev.gym.service.facade.impl.TrainerServiceFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(classes = {ServiceConfig.class, RepositoryConfig.class, SecurityConfig.class})
class TrainerServiceImplFacadeIT {

    @Autowired
    protected TrainerServiceFacade trainerServiceFacade;

    @Test
    void givenWrongUsernameAndPassword_whenDelete_thenThrowInvalidUsernameOrPasswordException() {
        String USERNAME = "wrongUsername";
        String PASSWORD = "wrongPassword";

        assertThrows(InvalidUsernameOrPasswordException.class,
                () -> trainerServiceFacade.deleteByUsername(USERNAME, PASSWORD)
        );
    }
}
