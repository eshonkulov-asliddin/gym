package dev.gym.repository.datasource.reader;

import dev.gym.model.Trainee;
import dev.gym.model.Trainer;
import dev.gym.model.Training;
import dev.gym.repository.config.RepositoryConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(10, trainees.size());
    }

    @Test
    void testReadTrainers() {
        var trainers = trainerFileReader.read();
        assertEquals(10, trainers.size());
    }

    @Test
    void testReadTrainings() {
        var trainings = trainingFileReader.read();
        assertEquals(10, trainings.size());
    }
}
