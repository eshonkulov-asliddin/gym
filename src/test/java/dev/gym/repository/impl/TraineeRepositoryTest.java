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

    private final ApplicationContext context;
    private final SimpleCredentialGenerator simpleCredentialGenerator;

    private Trainee trainee;

    @Autowired
    TraineeRepositoryTest(ApplicationContext context,
                          SimpleCredentialGenerator simpleCredentialGenerator) {
        this.context = context;
        this.simpleCredentialGenerator = simpleCredentialGenerator;
    }

    @BeforeEach
    void setUp(){
        // Given
        String date = "1990-01-01";
        String firstName = "John";
        String lasrtName = "Doe";
        boolean isActive = true;
        trainee = Trainee.builder()
                .firstName(firstName)
                .lastName(lasrtName)
                .credentialGenerator(simpleCredentialGenerator)
                .isActive(isActive)
                .dateOfBirth(LocalDate.parse(date))
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
        String newFirstName = "Jane";
        String UPDATED_FIRSTNAME = newFirstName;
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
