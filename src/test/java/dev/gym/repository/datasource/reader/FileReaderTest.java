package dev.gym.repository.datasource.reader;

import dev.gym.model.Trainee;
import dev.gym.model.Trainer;
import dev.gym.model.Training;
import dev.gym.repository.config.RepositoryConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = RepositoryConfig.class)
class FileReaderTest {

    private final FileReader<Trainee> traineeFileReader;
    private final FileReader<Trainer> trainerFileReader;
    private final FileReader<Training> trainingFileReader;

    @Autowired
    FileReaderTest(FileReader<Trainee> traineeFileReader,
                   FileReader<Trainer> trainerFileReader,
                   FileReader<Training> trainingFileReader) {
        this.traineeFileReader = traineeFileReader;
        this.trainerFileReader = trainerFileReader;
        this.trainingFileReader = trainingFileReader;
    }

    @Test
    void testReadTrainees() {
        var trainees = traineeFileReader.read();
        assertTrue(trainees.size() > 0);
    }

    @Test
    void testReadTrainers() {
        var trainers = trainerFileReader.read();
        assertTrue(trainers.size() > 0);
    }

    @Test
    void testReadTrainings() {
        var trainings = trainingFileReader.read();
        assertTrue(trainings.size() > 0);
    }
}
