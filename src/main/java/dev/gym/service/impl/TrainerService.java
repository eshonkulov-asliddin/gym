package dev.gym.service.impl;

import dev.gym.model.Trainer;
import dev.gym.repository.BaseRepository;
import dev.gym.service.BaseService;
import dev.gym.service.exception.NotFoundException;
import dev.gym.service.exception.util.ExceptionMsg;
import dev.gym.service.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
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
    public Map<Long, Trainer> findAll() {
        return trainerRepository.findAll();
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        Optional<Trainer> byId = trainerRepository.findById(id);
        if (byId.isEmpty()){
            logger.error(String.format(ExceptionMsg.NOT_FOUND_MESSAGE, "Trainer", id));
            throw new NotFoundException(String.format(ExceptionMsg.NOT_FOUND_MESSAGE, "Trainer", id));
        }
        return byId;
    }

    @Override
    public Trainer save(Trainer entity) {
        // Validate entity
        boolean isValid = trainerValidator.isValid(entity);
        if (!isValid){
            logger.error(String.format(ExceptionMsg.ILLIGAL_ARGUMENT_MESSAGE, "Trainer"));
            throw new IllegalArgumentException(String.format(ExceptionMsg.ILLIGAL_ARGUMENT_MESSAGE, "Trainer"));
        }
        return trainerRepository.save(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        logger.info("Delete trainer is not supported yet");
        throw new UnsupportedOperationException("Delete trainer is not supported yet");
    }
}
