package dev.gym.repository.impl;

import dev.gym.model.Trainer;
import dev.gym.repository.datasource.reader.Reader;
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
    private final Reader<Trainer> reader;

    @Autowired
    TrainerRepository(Map<Long, Trainer> data,
                      Reader<Trainer> reader) {
        super(logger, data);
        this.data = data;
        this.reader = reader;
    }
    @PostConstruct
    private void loadTrainers(){
       reader.read().forEach(trainer -> data.put(trainer.getId(), trainer));
    }
}
