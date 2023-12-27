package dev.gym.service.facade;

import dev.gym.repository.config.RepositoryConfig;
import dev.gym.security.config.SecurityConfig;
import dev.gym.service.config.ServiceConfig;
import dev.gym.service.exception.InvalidUsernameOrPasswordException;
import dev.gym.service.facade.impl.TraineeServiceFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(classes = {ServiceConfig.class, RepositoryConfig.class, SecurityConfig.class})
class TraineeServiceImplFacadeIT {

    @Autowired
    protected TraineeServiceFacade traineeServiceFacade;

    @Test
    void givenWrongUsernameAndPassword_whenGetAll_thenThrowInvalidUsernameOrPasswordException() {
        String USERNAME = "wrongUsername";
        String PASSWORD = "wrongPassword";

        assertThrows(InvalidUsernameOrPasswordException.class,
                () -> traineeServiceFacade.getAll(USERNAME, PASSWORD)
        );
    }
}
