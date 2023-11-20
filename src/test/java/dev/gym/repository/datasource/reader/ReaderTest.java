package dev.gym.repository.datasource.reader;

import dev.gym.model.Trainee;
import dev.gym.model.Trainer;
import dev.gym.model.Training;
import dev.gym.repository.config.RepositoryConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringJUnitConfig(classes = RepositoryConfig.class)
class ReaderTest {


    @Autowired private Reader<Trainee> traineeReader;
    @Autowired private Reader<Trainer> trainerReader;
    @Autowired private Reader<Training> trainingReader;

    @Test
    void testReadTrainees() {
        var trainees = traineeReader.read();
        assertFalse(trainees.isEmpty());
    }

    @Test
    void testReadTrainers() {
        var trainers = trainerReader.read();
        assertFalse(trainers.isEmpty());
    }

    @Test
    void testReadTrainings() {
        var trainings = trainingReader.read();
        assertFalse(trainings.isEmpty());
    }
}
