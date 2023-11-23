package dev.gym.service;

import dev.gym.config.AppConfig;
import dev.gym.model.Trainee;
import dev.gym.model.Trainer;
import dev.gym.model.Training;
import dev.gym.model.TrainingType;
import dev.gym.repository.datasource.credential.CredentialGenerator;
import dev.gym.service.exception.NotFoundException;
import dev.gym.service.impl.TrainingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringJUnitConfig(classes = AppConfig.class)
class TrainingServiceTest {

    private final TrainingService trainingService;
    private final CredentialGenerator credentialGenerator;

    @Autowired
    TrainingServiceTest(TrainingService trainingService, CredentialGenerator credentialGenerator) {
        this.trainingService = trainingService;
        this.credentialGenerator = credentialGenerator;
    }

    @Test
    void testCRUD(){
        // Mock data
        Trainee trainee = mock(Trainee.class);
        Trainer trainer = mock(Trainer.class);
        TrainingType trainingType = mock(TrainingType.class);
        String trainingName = "Test Training";
        LocalDate trainingDate = LocalDate.of(2021, 1, 1);
        int duration = 60;



        Training training = new Training(trainee,
                trainer,
                trainingName,
                trainingType,
                trainingDate,
                duration
        );

        // Save the training
        Training savedTraining = trainingService.save(training);
        assertNotNull(savedTraining);

        // Find the training by id
        assertTrue(trainingService.getById (savedTraining.getId()).isPresent());

        // Find all trainings
        assertFalse(trainingService.getAll ().isEmpty());

        // Delete the training
        assertThrows(UnsupportedOperationException.class, () -> trainingService.deleteById(savedTraining.getId()));

    }

    @Test
    void givenWrongArguments_whenCreate_thenThrowIllegalArgumentException(){
        // fields values
        Training training = new Training(null, null, null, null, null, 0);

        // Save the training
        assertThrows(IllegalArgumentException.class, () -> trainingService.save(training));

    }

    @Test
    void givenWrongId_whenFindById_thenThrowNotFoundException(){
        assertThrows(NotFoundException.class, () -> trainingService.getById (null));
    }
}
