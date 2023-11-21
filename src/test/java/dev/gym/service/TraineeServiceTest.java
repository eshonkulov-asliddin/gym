package dev.gym.service;

import dev.gym.config.AppConfig;
import dev.gym.model.Trainee;
import dev.gym.repository.datasource.credential.CredentialGenerator;
import dev.gym.service.exception.NotFoundException;
import dev.gym.service.impl.TraineeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;

@SpringJUnitConfig(classes = AppConfig.class)
class TraineeServiceTest {

    @Autowired private TraineeService traineeService;
    @Autowired private CredentialGenerator credentialGenerator;

    @Test
    void testCRUD(){
        // fields values
        String firstName = "John";
        String lastName = "Doe";;
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
        Assertions.assertTrue(traineeService.findById(savedTrainee.getId()).isPresent());

        // Find all trainees
        Assertions.assertFalse(traineeService.findAll().isEmpty());

        // Update the trainee
        savedTrainee.setFirstName("Jane");
        traineeService.save(savedTrainee);

        Assertions.assertEquals("Jane", traineeService.findById(savedTrainee.getId()).get().getFirstName());

        // Delete the trainee by id
        Assertions.assertTrue(traineeService.deleteById(savedTrainee.getId()));
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
    void givenWrongId_whenFindById_thenThrowNotFoundException(){
        Assertions.assertThrows(NotFoundException.class, () -> traineeService.findById(null));
    }
}
