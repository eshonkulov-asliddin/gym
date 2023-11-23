package dev.gym.service;

import dev.gym.config.AppConfig;
import dev.gym.model.Trainee;
import dev.gym.repository.datasource.credential.CredentialGenerator;
import dev.gym.service.impl.TraineeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = AppConfig.class)
class TraineeServiceTest {

    private final TraineeService traineeService;
    private final CredentialGenerator credentialGenerator;

    @Autowired
    TraineeServiceTest(TraineeService traineeService,
                       CredentialGenerator credentialGenerator) {
        this.traineeService = traineeService;
        this.credentialGenerator = credentialGenerator;
    }

    @Test
    void testCRUD(){
        // fields values
        String firstName = "John";
        String lastName = "Doe";
        boolean isActive = true;

        // Create a new trainee with Builder
        Trainee trainee = Trainee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .isActive(isActive)
                .credentialGenerator(credentialGenerator)
                .dateOfBirth(LocalDate.now())
                .address("123 Main St.").build();

        // Save the trainee
        Trainee savedTrainee = traineeService.save(trainee);

        // Find the trainee by id
        assertTrue(traineeService.getById (savedTrainee.getId()).isPresent());

        // Find all trainees
        Assertions.assertFalse(traineeService.getAll ().isEmpty());

        // Update the trainee
        savedTrainee.setFirstName("Jane");
        traineeService.save(savedTrainee);

        Assertions.assertEquals("Jane", traineeService.getById (savedTrainee.getId()).get().getFirstName());

        // Delete the trainee by id
        assertTrue(traineeService.deleteById(savedTrainee.getId()));
    }

    @Test
    void givenWrongArguments_whenCreate_thenThrowIllegalArgumentException(){
        // fields values
        Trainee trainee = Trainee.builder()
                .credentialGenerator(credentialGenerator)
                .build();

        // Save the trainee
        Assertions.assertThrows(IllegalArgumentException.class, () -> traineeService.save(trainee));

    }

    @Test
    void givenWrongId_whenFindById_thenReturnOptionalEmpty(){
        assertTrue(traineeService.getById (null).isEmpty());
    }
}
