package dev.gym.repository.impl;

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

import java.time.LocalDate;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TrainingRepositoryImplIT {

    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private Training savedTraining;

    @Autowired
    public TrainingRepositoryImplIT(TrainingRepository trainingRepository,
                                    TrainingTypeRepository trainingTypeRepository) {
        this.trainingRepository = trainingRepository;
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @BeforeEach
    void setUp() {
        // Fields for Trainee
        String TRAINEE_FIRSTNAME = "John";
        String TRAINEE_LASTNAME = "Doe";

        Trainee trainee = new Trainee();
        trainee.setFirstName(TRAINEE_FIRSTNAME);
        trainee.setLastName(TRAINEE_LASTNAME);
        trainee.setUsername(randomAlphabetic(10));
        trainee.setPassword(randomAlphabetic(10));

        // Fields for Trainer
        String TRAINER_FIRSTNAME = randomAlphabetic(10);
        String TRAINER_LASTNAME = randomAlphabetic(10);

        Trainer trainer = new Trainer();
        trainer.setFirstName(TRAINER_FIRSTNAME);
        trainer.setLastName(TRAINER_LASTNAME);
        trainer.setUsername(randomAlphabetic(10));
        trainer.setPassword(randomAlphabetic(10));

        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName(randomAlphabetic(10));
        training.setTrainingType(trainingTypeRepository.findByTrainingTypeName(TrainingTypeEnum.STRENGTH));
        training.setTrainingDate(LocalDate.of(2024, 1, 1));
        training.setTrainingDuration(60);
        trainingRepository.save(training);
        savedTraining = trainingRepository.findById(training.getId())
                .orElseThrow(() -> new NoResultException("No training found!"));
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
        trainingRepository.delete(savedTraining);
        assertNotNull(trainingRepository.findById(savedTraining.getId()));
    }
}
