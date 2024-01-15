package dev.gym.repository.impl;

import dev.gym.repository.TraineeRepository;
import dev.gym.repository.TrainingRepository;
import dev.gym.repository.TrainingTypeRepository;
import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;
import dev.gym.repository.model.enums.TrainingTypeEnum;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.transaction.TestTransaction;

import java.time.LocalDate;
import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TraineeRepositoryImplIT {

    @Autowired
    private TraineeRepository traineeRepository;
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private TrainingTypeRepository trainingTypeRepository;
    private Trainee savedTrainee;

    @BeforeEach
    void setUp() {
        String FIRSTNAME = randomAlphabetic(10);
        String LASTNAME = randomAlphabetic(10);
        LocalDate DATE_OF_BIRTH = LocalDate.of(1990, 1, 1);
        String generatedUsername = randomAlphabetic(10);
        String generatedPassword = randomAlphabetic(10);

        Trainee trainee = new Trainee();
        trainee.setFirstName(FIRSTNAME);
        trainee.setLastName(LASTNAME);
        trainee.setUsername(generatedUsername);
        trainee.setPassword(generatedPassword);
        trainee.setDateOfBirth(DATE_OF_BIRTH);

        traineeRepository.save(trainee);
        savedTrainee = traineeRepository.findByUsername(generatedUsername)
                .orElseThrow(() -> new NoResultException("No trainee found!"));
    }

    @Test
    void givenValidEntity_whenSave_thenSuccess() {
        assertNotNull(savedTrainee);
    }

    @Test
    void givenValidId_whenFindById_thenSuccess() {
        Optional<Trainee> byId = traineeRepository.findById(savedTrainee.getId());
        assertTrue(byId.isPresent());
    }

    @Test
    void givenValidUsername_whenFindByUsername_thenSuccess() {
        Optional<Trainee> byUsername = traineeRepository.findByUsername(savedTrainee.getUsername());
        assertTrue(byUsername.isPresent());
    }

    @Test
    void givenInvalidUsername_whenFindByUsername_thenEmpty() {
        assertTrue(traineeRepository.findByUsername("invalidUsername").isEmpty());
    }

    @Test
    void givenValidUsername_whenDeleteByUsername_thenDelete() {
        // Delete the trainee
        traineeRepository.deleteByUsername(savedTrainee.getUsername());

        // Assert the trainee is deleted
        assertTrue(traineeRepository.findByUsername(savedTrainee.getUsername()).isEmpty());
    }

    @Test
    void givenValidUsername_whenUpdatePassword_thenUpdateAndCheck() {
        String newPassword = randomAlphabetic(10);
        traineeRepository.updatePasswordByUsername(savedTrainee.getUsername(), newPassword);
        // Commit the transaction
        TestTransaction.flagForCommit();
        TestTransaction.end();

        // Retrieve the Trainee from the repository after the update
        Optional<Trainee> trainee = traineeRepository.findByUsername(savedTrainee.getUsername());

        // Check if the Trainee is present
        assertTrue(trainee.isPresent());
        // Check if the password is updated
        assertEquals(newPassword, trainee.get().getPassword());
    }

    @Test
    void givenInvalidUsername_whenUpdatePassword_thenPasswordNotChanged() {
        String newPassword = randomAlphabetic(10);
        traineeRepository.updatePasswordByUsername(randomAlphabetic(5), newPassword);
        Optional<Trainee> byId = traineeRepository.findById(savedTrainee.getId());
        assertTrue(byId.isPresent());
        assertEquals(byId.get().getPassword(), savedTrainee.getPassword());
    }

    @Test
    void whenSetActiveStatus_thenStatusChanged() {
        traineeRepository.setActiveStatusByUsername(savedTrainee.getUsername(), false);
        Optional<Trainee> byId = traineeRepository.findById(savedTrainee.getId());
        assertTrue(byId.isPresent());
        assertFalse(byId.get().isActive());
    }

    @Test
    void givenValidUsername_whenFindAllTrainingsByUsername_thenSuccess() {
        Training training = createTraining();
        assertFalse(trainingRepository.findFor(savedTrainee.getUsername(), null, null, null).isEmpty());
    }

    private Training createTraining() {
        // Fields for Trainer
        String TRAINER_FIRSTNAME = randomAlphabetic(10);
        String TRAINER_LASTNAME = randomAlphabetic(10);

        Trainer trainer = new Trainer();
        trainer.setFirstName(TRAINER_FIRSTNAME);
        trainer.setLastName(TRAINER_LASTNAME);
        trainer.setUsername(randomAlphabetic(10));
        trainer.setPassword(randomAlphabetic(10));

        Training training = new Training();
        training.setTrainee(savedTrainee);
        training.setTrainer(trainer);
        training.setTrainingName(randomAlphabetic(6));
        training.setTrainingType(trainingTypeRepository.findByTrainingTypeName(TrainingTypeEnum.CARDIO));
        training.setTrainingDate(LocalDate.of(2024, 1, 1));
        training.setTrainingDuration(60);

        trainingRepository.save(training);
        return trainingRepository.findById(training.getId()).orElseThrow(() -> new NoResultException("No training found!"));
    }
}
