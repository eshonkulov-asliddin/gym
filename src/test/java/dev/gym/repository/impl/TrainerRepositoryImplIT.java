package dev.gym.repository.impl;

import dev.gym.repository.TrainerRepository;
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
class TrainerRepositoryImplIT {

    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private Trainer savedTrainer;

    @Autowired
    public TrainerRepositoryImplIT(TrainerRepository trainerRepository,
                                   TrainingRepository trainingRepository,
                                   TrainingTypeRepository trainingTypeRepository) {
        this.trainerRepository = trainerRepository;
        this.trainingRepository = trainingRepository;
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @BeforeEach
    void setUp() {
        String FIRSTNAME = randomAlphabetic(10);
        String LASTNAME = randomAlphabetic(10);

        String generatedUsername = randomAlphabetic(10);
        String generatedPassword = randomAlphabetic(10);

        Trainer trainee = new Trainer();
        trainee.setFirstName(FIRSTNAME);
        trainee.setLastName(LASTNAME);
        trainee.setUsername(generatedUsername);
        trainee.setPassword(generatedPassword);
        trainee.setSpecialization(trainingTypeRepository.findByTrainingTypeName(TrainingTypeEnum.STRENGTH));

        trainerRepository.save(trainee);
        savedTrainer = trainerRepository.findByUsername(generatedUsername)
                .orElseThrow(() -> new NoResultException("No trainer found!"));
    }

    @Test
    void givenValidEntity_whenSave_thenSuccess() {
        assertNotNull(savedTrainer);
        // Retrieve the trainer from the database after the save operation
        Trainer retrievedTrainer = trainerRepository.findByUsername(savedTrainer.getUsername())
                .orElseThrow(() -> new NoResultException("No trainer found!"));
        assertNotNull(retrievedTrainer);
    }

    @Test
    void givenValidId_whenFindById_thenSuccess() {
        Optional<Trainer> trainer = trainerRepository.findById(savedTrainer.getId());
        assertTrue(trainer.isPresent());
    }

    @Test
    void givenValidUsername_whenFindByUsername_thenSuccess() {
        Optional<Trainer> byUsername = trainerRepository.findByUsername(savedTrainer.getUsername());
        assertTrue(byUsername.isPresent());
    }

    @Test
    void givenValidUsername_whenDeleteByUsername_thenDelete() {
        trainerRepository.deleteByUsername(savedTrainer.getUsername());
        assertTrue(trainerRepository.findByUsername(savedTrainer.getUsername()).isEmpty());
    }

    @Test
    void givenValidUsername_whenUpdatePassword_thenUpdate() {
        String newPassword = randomAlphabetic(10);
        trainerRepository.updatePasswordByUsername(savedTrainer.getUsername(), newPassword);
        // Commit the transaction
        TestTransaction.flagForCommit();
        TestTransaction.end();

        // Retrieve the trainer from the database after the update operation
        Optional<Trainer> trainer = trainerRepository.findById(savedTrainer.getId());
        assertTrue(trainer.isPresent());
        assertEquals(trainer.get().getPassword(), newPassword);
    }

    @Test
    void givenInvalidUsername_whenUpdatePassword_thenPasswordNotChanged() {
        String newPassword = randomAlphabetic(10);
        trainerRepository.updatePasswordByUsername(randomAlphabetic(5), newPassword);
        Optional<Trainer> trainer = trainerRepository.findById(savedTrainer.getId());
        assertTrue(trainer.isPresent());
        assertEquals(trainer.get().getPassword(), savedTrainer.getPassword());
    }

    @Test
    void givenInValidUsername_whenFindByUsername_thenEmpty() {
        assertTrue( trainerRepository.findByUsername(randomAlphabetic(5)).isEmpty());
    }

    @Test
    void givenValidTrainer_whenSetActiveStatus_thenStatusChanged() {
        trainerRepository.setActiveStatusByUsername(savedTrainer.getUsername(), true);
        // Commit the transaction
        TestTransaction.flagForCommit();
        TestTransaction.end();

        // Retrieve the trainer from the database after the update operation
        Optional<Trainer> trainer = trainerRepository.findById(savedTrainer.getId());
        assertTrue(trainer.isPresent());
        assertTrue(trainer.get().isActive());
    }

    @Test
    void givenValidUsername_whenFindAllTrainingsByUsername_thenSuccess() {
        Training training = createTraining();
        assertFalse(trainingRepository.findFor(null, savedTrainer.getUsername(), null, null).isEmpty());
    }

    private Training createTraining() {
        // Fields for Trainee
        String TRAINEE_FIRSTNAME = randomAlphabetic(10);
        String TRAINEE_LASTNAME = randomAlphabetic(10);

        Trainee trainee = new Trainee();
        trainee.setFirstName(TRAINEE_FIRSTNAME);
        trainee.setLastName(TRAINEE_LASTNAME);
        trainee.setUsername(randomAlphabetic(10));
        trainee.setPassword(randomAlphabetic(10));


        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(savedTrainer);
        training.setTrainingName(randomAlphabetic(10));
        training.setTrainingType(savedTrainer.getSpecialization());
        training.setTrainingDate(LocalDate.of(2024, 1, 1));
        training.setTrainingDuration(60);
        trainingRepository.save(training);
        return trainingRepository.findById(training.getId())
                .orElseThrow(() -> new NoResultException("No training found!"));
    }

}
