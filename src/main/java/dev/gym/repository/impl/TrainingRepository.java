package dev.gym.repository.impl;

import dev.gym.model.Training;
import dev.gym.repository.datasource.reader.FileReader;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainingRepository extends AbstractRepository<Training, Long> {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TrainingRepository.class);
    private final Map<Long, Training> data;
    private final FileReader<Training> fileReader;

    TrainingRepository(Map<Long, Training> data, FileReader<Training> fileReader) {
        super(logger, data);
        this.data = data;
        this.fileReader = fileReader;
    }

    @PostConstruct
    private void loadTrainings(){
        fileReader.read().forEach(training -> {
            data.put(training.getId(), training);
        });
    }

}
