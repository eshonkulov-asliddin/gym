package dev.gym.repository;

import dev.gym.config.AppConfig;
import dev.gym.model.Trainee;
import dev.gym.model.Trainer;
import dev.gym.model.Training;
import dev.gym.model.TrainingType;
import dev.gym.model.enums.TrainingTypeEnum;
import dev.gym.repository.datasource.credential.CredentialGenerator;
import dev.gym.repository.impl.AbstractCrudRepository;
import dev.gym.repository.impl.AbstractUserRepository;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = AppConfig.class)
class TraineeRepositoryIT {

    @Autowired
    protected AbstractUserRepository<Trainee, Training, Long> traineeRepository;

    @Autowired
    protected AbstractCrudRepository<Training, Long> trainingRepository;

    @Autowired
    protected CredentialGenerator credentialGenerator;

    private Trainee savedTrainee;

    @BeforeEach
    void setUp() {
        String FIRSTNAME = "John";
        String LASTNAME = "Doe";
        LocalDate DATE_OF_BIRTH = LocalDate.of(1990, 1, 1);

        Trainee trainee = new Trainee();
        trainee.setFirstName(FIRSTNAME);
        trainee.setLastName(LASTNAME);
        trainee.setUsername(credentialGenerator.generateUsername(FIRSTNAME, LASTNAME));
        trainee.setPassword(credentialGenerator.generatePassword());
        trainee.setDateOfBirth(DATE_OF_BIRTH);

        savedTrainee = traineeRepository.save(trainee);
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
    void givenInvalidUsername_whenFindByUsername_thenThrowNoResultException() {
        assertThrows(NoResultException.class, () -> traineeRepository.findByUsername("invalidUsername"));
    }

    @Test
    void givenValidUsername_whenDeleteByUsername_thenDelete() {
        // Delete the trainee
        traineeRepository.deleteByUsername(savedTrainee.getUsername());

        // Assert the trainee is deleted
        assertThrows(NoResultException.class,
                () -> traineeRepository.findByUsername(savedTrainee.getUsername())
        );
    }

    @Test
    void givenValidId_whenUpdatePassword_thenUpdate() {
        String newPassword = credentialGenerator.generatePassword();
        traineeRepository.updatePassword(savedTrainee.getId(), newPassword);
        Optional<Trainee> byId = traineeRepository.findById(savedTrainee.getId());
        assertTrue(byId.isPresent());
        assertEquals(byId.get().getPassword(), newPassword);
    }

    @Test
    void givenInvalidId_whenUpdatePassword_thenPasswordNotChanged() {
        String newPassword = credentialGenerator.generatePassword();
        traineeRepository.updatePassword(-1L, newPassword);
        Optional<Trainee> byId = traineeRepository.findById(savedTrainee.getId());
        assertTrue(byId.isPresent());
        assertEquals(byId.get().getPassword(), savedTrainee.getPassword());
    }

    @Test
    void whenSetActiveStatus_thenStatusChanged() {
        traineeRepository.setActiveStatus(savedTrainee.getId(), false);
        Optional<Trainee> byId = traineeRepository.findById(savedTrainee.getId());
        assertTrue(byId.isPresent());
        assertFalse(byId.get().isActive());
    }

    @Test
    void givenValidUsername_whenFindAllTrainingsByUsername_thenSuccess() {

        Training training = createTraining();
        assertTrue(traineeRepository.findAllTrainingsByUsername(savedTrainee.getUsername()).size() > 0);
    }

    private Training createTraining() {
        // Fields for Trainer
        String TRAINER_FIRSTNAME = "Tom";
        String TRAINER_LASTNAME = "Jerry";

        Trainer trainer = new Trainer();
        trainer.setFirstName(TRAINER_FIRSTNAME);
        trainer.setLastName(TRAINER_LASTNAME);
        trainer.setUsername(credentialGenerator.generateUsername(TRAINER_FIRSTNAME, TRAINER_LASTNAME));
        trainer.setPassword(credentialGenerator.generatePassword());

        // Fields for TrainingType
        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName(TrainingTypeEnum.STRENGTH);


        Training training = new Training();
        training.setTrainee(savedTrainee);
        training.setTrainer(trainer);
        training.setTrainingName("Test Training");
        training.setTrainingType(trainingType);
        training.setTrainingDate(LocalDate.of(2024, 1, 1));
        training.setTrainingDuration(60);

        return trainingRepository.save(training);
    }

}
