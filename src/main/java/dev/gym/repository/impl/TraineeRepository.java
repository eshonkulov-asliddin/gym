package dev.gym.repository.impl;

import dev.gym.model.Trainee;
import dev.gym.repository.datasource.reader.FileReader;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TraineeRepository extends AbstractRepository<Trainee, Long> {

    private static final Logger logger = LoggerFactory.getLogger(TraineeRepository.class);
    @Getter
    private final Map<Long, Trainee> data;
    private final FileReader<Trainee> fileReader;

    @Autowired
    TraineeRepository(Map<Long, Trainee> data,
                      FileReader<Trainee> fileReader) {
        super(logger, data);
        this.data = data;
        this.fileReader = fileReader;
    }

    @PostConstruct
    private void loadTrainees(){
        fileReader.read().forEach(trainee -> {
            data.put(trainee.getId(), trainee);
        });
    }
}