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
class TrainerRepositoryIT {

    @Autowired
    protected AbstractUserRepository<Trainer, Training, Long> trainerRepository;

    @Autowired
    protected AbstractCrudRepository<Training, Long> trainingRepository;

    @Autowired
    protected CredentialGenerator credentialGenerator;

    private Trainer savedTrainer;

    @BeforeEach
    void setUp() {
        String FIRSTNAME = "John";
        String LASTNAME = "Doe";

        Trainer trainee = new Trainer();
        trainee.setFirstName(FIRSTNAME);
        trainee.setLastName(LASTNAME);
        trainee.setUsername(credentialGenerator.generateUsername(FIRSTNAME, LASTNAME));
        trainee.setPassword(credentialGenerator.generatePassword());

        savedTrainer = trainerRepository.save(trainee);
    }

    @Test
    void givenValidEntity_whenSave_thenSuccess() {
        assertNotNull(savedTrainer);
    }

    @Test
    void givenValidId_whenFindById_thenSuccess() {
        Optional<Trainer> byId = trainerRepository.findById(savedTrainer.getId());
        assertTrue(byId.isPresent());
    }

    @Test
    void givenValidUsername_whenFindByUsername_thenSuccess() {
        Optional<Trainer> byUsername = trainerRepository.findByUsername(savedTrainer.getUsername());
        assertTrue(byUsername.isPresent());
    }

    @Test
    void givenValidUsername_whenDeleteByUsername_thenDelete() {
        trainerRepository.deleteByUsername(savedTrainer.getUsername());
        assertThrows(NoResultException.class,
                () -> trainerRepository.findByUsername(savedTrainer.getUsername())
        );
    }

    @Test
    void givenValidId_whenUpdatePassword_thenUpdate() {
        String newPassword = credentialGenerator.generatePassword();
        trainerRepository.updatePassword(savedTrainer.getId(), newPassword);
        Optional<Trainer> byId = trainerRepository.findById(savedTrainer.getId());
        assertTrue(byId.isPresent());
        assertEquals(byId.get().getPassword(), newPassword);
    }

    @Test
    void givenInvalidId_whenUpdatePassword_thenPasswordNotChanged() {
        String newPassword = credentialGenerator.generatePassword();
        trainerRepository.updatePassword(-1L, newPassword);
        Optional<Trainer> byId = trainerRepository.findById(savedTrainer.getId());
        assertTrue(byId.isPresent());
        assertEquals(byId.get().getPassword(), savedTrainer.getPassword());
    }

    @Test
    void givenInValidUsername_whenFindByUsername_thenThrowNoResultException() {
        assertThrows(NoResultException.class,
                () -> trainerRepository.findByUsername("invalid")
        );
    }

    @Test
    void whenSetActiveStatus_thenStatusChanged() {
        trainerRepository.setActiveStatus(savedTrainer.getId(), true);
        Optional<Trainer> byId = trainerRepository.findById(savedTrainer.getId());
        assertTrue(byId.isPresent());
        assertTrue(byId.get().isActive());
    }

    @Test
    void givenValidUsername_whenFindAllTrainingsByUsername_thenSuccess() {
        Training training = createTraining();
        assertFalse(trainerRepository.findAllTrainingsByUsername(savedTrainer.getUsername()).isEmpty());
    }

    private Training createTraining() {
        // Fields for Trainee
        String TRAINEE_FIRSTNAME = "Tom";
        String TRAINEE_LASTNAME = "Jerry";

        Trainee trainee = new Trainee();
        trainee.setFirstName(TRAINEE_FIRSTNAME);
        trainee.setLastName(TRAINEE_LASTNAME);
        trainee.setUsername(credentialGenerator.generateUsername(TRAINEE_FIRSTNAME, TRAINEE_LASTNAME));
        trainee.setPassword(credentialGenerator.generatePassword());

        // Fields for TrainingType
        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName(TrainingTypeEnum.STRENGTH);


        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(savedTrainer);
        training.setTrainingName("Test Training");
        training.setTrainingType(trainingType);
        training.setTrainingDate(LocalDate.of(2024, 1, 1));
        training.setTrainingDuration(60);
        return trainingRepository.save(training);
    }

}
