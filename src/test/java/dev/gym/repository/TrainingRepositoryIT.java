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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(classes = AppConfig.class)
class TrainingRepositoryIT {

    @Autowired
    protected AbstractCrudRepository<Training, Long> trainingRepository;

    @Autowired
    protected AbstractUserRepository<Trainee, Training, Long> traineeRepository;

    @Autowired
    protected AbstractUserRepository<Trainer, Training, Long> trainerRepository;

    @Autowired
    protected CredentialGenerator credentialGenerator;

    protected Training savedTraining;

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

        // Fields for TrainingType
        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName(TrainingTypeEnum.STRENGTH);


        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName("Test Training");
        training.setTrainingType(trainingType);
        training.setTrainingDate(LocalDate.of(2024, 1, 1));
        training.setTrainingDuration(60);
        savedTraining = trainingRepository.save(training);
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