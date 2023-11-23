package dev.gym.repository.impl;

import dev.gym.model.Trainer;
import dev.gym.repository.datasource.reader.FileReader;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainerRepository extends AbstractRepository<Trainer, Long> {
    private static final Logger logger = LoggerFactory.getLogger(TrainerRepository.class);
    @Getter
    private final Map<Long, Trainer> data;
    private final FileReader<Trainer> fileReader;

    @Autowired
    TrainerRepository(Map<Long, Trainer> data,
                      FileReader<Trainer> fileReader) {
        super(logger, data);
        this.data = data;
        this.fileReader = fileReader;
    }
    @PostConstruct
    private void loadTrainers(){
       fileReader.read().forEach(trainer -> {
           data.put(trainer.getId(), trainer);
       });
    }
}
