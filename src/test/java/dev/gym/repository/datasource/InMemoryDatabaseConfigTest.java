package dev.gym.repository.datasource;

import dev.gym.repository.config.RepositoryConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = RepositoryConfig.class)
class InMemoryDatabaseConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void givenInMemoryStores_whenStoresIsNotEmpty_thenOk(){

        var trainer = context.getBean("trainerStore", Map.class);
        var trainee = context.getBean("traineeStore", Map.class);
        var training = context.getBean("trainingStore", Map.class);
        var username = context.getBean("usernameStore", Map.class);

        int expectedSize = 0;
        assertTrue(trainer.size() > expectedSize);
        assertTrue(trainee.size() > expectedSize);
        assertTrue(training.size() > expectedSize);
        assertTrue(username.size() > expectedSize);
    }

}
