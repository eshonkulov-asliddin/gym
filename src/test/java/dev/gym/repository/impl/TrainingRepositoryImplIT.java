package dev.gym.repository.impl;

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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(classes = RepositoryConfig.class)
class TrainingRepositoryImplIT {

    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final CredentialGenerator credentialGenerator;
    private Training savedTraining;

    @Autowired
    public TrainingRepositoryImplIT(TrainingRepository trainingRepository,
                                    TrainingTypeRepository trainingTypeRepository,
                                    CredentialGenerator credentialGenerator) {
        this.trainingRepository = trainingRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.credentialGenerator = credentialGenerator;
    }

    @BeforeEach
    void setUp() {
        // Fields for Trainee
        String TRAINEE_FIRSTNAME = "John";
        String TRAINEE_LASTNAME = "Doe";

        Trainee trainee = new Trainee();
        trainee.setFirstName(TRAINEE_FIRSTNAME);
        trainee.setLastName(TRAINEE_LASTNAME);
        trainee.setUsername(credentialGenerator.generateUsername(TRAINEE_FIRSTNAME, TRAINEE_LASTNAME));
        trainee.setPassword(credentialGenerator.generatePassword());

        // Fields for Trainer
        String TRAINER_FIRSTNAME = "Tom";
        String TRAINER_LASTNAME = "Jerry";

        Trainer trainer = new Trainer();
        trainer.setFirstName(TRAINER_FIRSTNAME);
        trainer.setLastName(TRAINER_LASTNAME);
        trainer.setUsername(credentialGenerator.generateUsername(TRAINER_FIRSTNAME, TRAINER_LASTNAME));
        trainer.setPassword(credentialGenerator.generatePassword());

        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName("Test Training");
        training.setTrainingType(trainingTypeRepository.findByType(TrainingTypeEnum.STRENGTH));
        training.setTrainingDate(LocalDate.of(2024, 1, 1));
        training.setTrainingDuration(60);
        trainingRepository.save(training);
        savedTraining = trainingRepository.findById(training.getId()).orElseThrow(() -> new NoResultException("No training found!"));
    }

    @Test
    void givenValidEntity_whenSave_thenSuccess() {
        assertNotNull(savedTraining.getId());
    }

    @Test
    void givenValidId_whenFindById_thenSuccess() {
        assertNotNull(trainingRepository.findById(savedTraining.getId()));
    }

    @Test
    void givenValidId_whenDelete_thenSuccess() {
        trainingRepository.delete(savedTraining.getId());
        assertNotNull(trainingRepository.findById(savedTraining.getId()));
    }

    @Test
    void givenValidId_whenDeleteById_thenSuccess() {
        trainingRepository.delete(savedTraining.getId());
        assertFalse(trainingRepository.findById(savedTraining.getId()).isPresent());
    }

}
