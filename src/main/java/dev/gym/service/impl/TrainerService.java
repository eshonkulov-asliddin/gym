package dev.gym.service.impl;

import dev.gym.model.Trainer;
import dev.gym.repository.BaseRepository;
import dev.gym.service.BaseService;
import dev.gym.service.exception.util.ExceptionMsg;
import dev.gym.service.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerService implements BaseService<Trainer, Long> {
    private static final Logger logger = LoggerFactory.getLogger(TrainerService.class);
    private final BaseRepository<Trainer, Long> trainerRepository;
    private final Validator<Trainer> trainerValidator;

    @Autowired
    public TrainerService(BaseRepository<Trainer, Long> trainerRepository,
                          Validator<Trainer> trainerValidator) {
        this.trainerRepository = trainerRepository;
        this.trainerValidator = trainerValidator;
    }

    @Override
    public List<Trainer> getAll() {
        return trainerRepository.findAll();
    }

    @Override
    public Optional<Trainer> getById(Long id) {
        Optional<Trainer> trainer = trainerRepository.findProxyById(id);
        if ( trainer.isEmpty() ){
            logger.error(String.format(ExceptionMsg.NOT_FOUND_MESSAGE, "Trainer", id));
            return Optional.empty();
        }
        return trainer;
    }

    @Override
    public Trainer save(Trainer entity) {
        // Validate entity
        trainerValidator.validate (entity);

        return trainerRepository.save(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        logger.info("Delete trainer is not supported yet");
        throw new UnsupportedOperationException("Delete trainer is not supported yet");
    }
}
