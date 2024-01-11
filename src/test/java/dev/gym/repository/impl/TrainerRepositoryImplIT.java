package dev.gym.repository.impl;

import dev.gym.repository.TrainerRepository;
import dev.gym.repository.TrainingRepository;
import dev.gym.repository.TrainingTypeRepository;
import dev.gym.repository.config.RepositoryConfig;
import dev.gym.repository.datasource.credential.CredentialGenerator;
import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;
import dev.gym.repository.model.enums.TrainingTypeEnum;
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

@SpringJUnitConfig(classes = RepositoryConfig.class)
class TrainerRepositoryImplIT {

    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final CredentialGenerator credentialGenerator;
    private Trainer savedTrainer;

    @Autowired
    public TrainerRepositoryImplIT(TrainerRepository trainerRepository,
                                   TrainingRepository trainingRepository,
                                   TrainingTypeRepository trainingTypeRepository,
                                   CredentialGenerator credentialGenerator) {
        this.trainerRepository = trainerRepository;
        this.trainingRepository = trainingRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.credentialGenerator = credentialGenerator;
    }

    @BeforeEach
    void setUp() {
        String FIRSTNAME = "John";
        String LASTNAME = "Doe";

        String generatedUsername = credentialGenerator.generateUsername(FIRSTNAME, LASTNAME);
        String generatedPassword = credentialGenerator.generatePassword();

        Trainer trainee = new Trainer();
        trainee.setFirstName(FIRSTNAME);
        trainee.setLastName(LASTNAME);
        trainee.setUsername(generatedUsername);
        trainee.setPassword(generatedPassword);
        trainee.setSpecialization(trainingTypeRepository.findByType(TrainingTypeEnum.STRENGTH));

        trainerRepository.save(trainee);
        savedTrainer = trainerRepository.findByUsername(generatedUsername)
                .orElseThrow(() -> new NoResultException("No trainer found!"));
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
    void givenValidUsername_whenUpdatePassword_thenUpdate() {
        String newPassword = credentialGenerator.generatePassword();
        trainerRepository.updatePassword(savedTrainer.getUsername(), newPassword);
        Optional<Trainer> byId = trainerRepository.findById(savedTrainer.getId());
        assertTrue(byId.isPresent());
        assertEquals(byId.get().getPassword(), newPassword);
    }

    @Test
    void givenInvalidUsername_whenUpdatePassword_thenPasswordNotChanged() {
        String newPassword = credentialGenerator.generatePassword();
        trainerRepository.updatePassword("username", newPassword);
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
    void givenValidTrainer_whenSetActiveStatus_thenStatusChanged() {
        trainerRepository.setActiveStatus(savedTrainer.getUsername(), true);
        Optional<Trainer> byId = trainerRepository.findById(savedTrainer.getId());
        assertTrue(byId.isPresent());
        assertTrue(byId.get().isActive());
    }

    @Test
    void givenValidUsername_whenFindAllTrainingsByUsername_thenSuccess() {
        Training training = createTraining();
        assertFalse(trainingRepository.findFor(null, savedTrainer.getUsername(), null, null).isEmpty());
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


        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(savedTrainer);
        training.setTrainingName("Test Training");
        training.setTrainingType(savedTrainer.getSpecialization());
        training.setTrainingDate(LocalDate.of(2024, 1, 1));
        training.setTrainingDuration(60);
        trainingRepository.save(training);
        return trainingRepository.findById(training.getId()).orElseThrow(() -> new NoResultException("No training found!"));
    }

}
