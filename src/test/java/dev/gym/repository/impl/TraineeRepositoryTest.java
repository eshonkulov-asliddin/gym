package dev.gym.repository.impl;

import dev.gym.model.Trainee;
import dev.gym.repository.config.RepositoryConfig;
import dev.gym.repository.datasource.credential.impl.SimpleCredentialGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = RepositoryConfig.class)
class TraineeRepositoryTest {

    @Autowired private ApplicationContext context;
    @Autowired private SimpleCredentialGenerator simpleCredentialGenerator;

    private Trainee trainee;

    @BeforeEach
    void setUp(){
        // Given
        trainee = new Trainee.Builder("John", "Doe", simpleCredentialGenerator, true)
                .dateOfBirth(LocalDate.parse("1990-01-01"))
                .build();
    }

    @Test
    void testFindById() {
        TraineeRepository traineeRepository = context.getBean(TraineeRepository.class);

        // When
        traineeRepository.save(trainee);

        // Then
        assertTrue(traineeRepository.findById(trainee.getId()).isPresent());
    }

    @Test
    void testFindAll() {
        TraineeRepository traineeRepository = context.getBean(TraineeRepository.class);

        // Assert
        assertNotNull(traineeRepository.findAll());
    }

    @Test
    void testSaveTrainee() {
        TraineeRepository traineeRepository = context.getBean(TraineeRepository.class);

        // When
        traineeRepository.save(trainee);

        // Then
        assertTrue(traineeRepository.getData().containsKey(trainee.getId()));
    }

    @Test
    void testUpdateTrainee() {
        TraineeRepository traineeRepository = context.getBean(TraineeRepository.class);

        // When
        traineeRepository.save(trainee);
        String UPDATED_FIRSTNAME = "Jane";
        trainee.setFirstName(UPDATED_FIRSTNAME);
        traineeRepository.save(trainee);

        // Then
        assertEquals(UPDATED_FIRSTNAME, traineeRepository.getData().get(trainee.getId()).getFirstName());
    }

    @Test
    void testDeleteTrainee() {
        TraineeRepository traineeRepository = context.getBean(TraineeRepository.class);

        // When
        traineeRepository.save(trainee);
        traineeRepository.deleteById(trainee.getId());

        // Then
        assertFalse(traineeRepository.getData().containsKey(trainee.getId()));
    }
}