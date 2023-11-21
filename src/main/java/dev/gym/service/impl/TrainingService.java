package dev.gym.service.impl;

import dev.gym.model.Training;
import dev.gym.repository.BaseRepository;
import dev.gym.service.BaseService;
import dev.gym.service.exception.NotFoundException;
import dev.gym.service.exception.util.ExceptionMsg;
import dev.gym.service.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class TrainingService implements BaseService<Training, Long> {

    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);
    private final BaseRepository<Training, Long> trainingRepository;
    private final Validator<Training> trainingValidator;

    public TrainingService(BaseRepository<Training, Long> trainingRepository,
                           Validator<Training> trainingValidator) {
        this.trainingRepository = trainingRepository;
        this.trainingValidator = trainingValidator;
    }

    @Override
    public Map<Long, Training> findAll() {
        return trainingRepository.findAll();
    }

    @Override
    public Optional<Training> findById(Long id) {
        Optional<Training> byId = trainingRepository.findById(id);
        if (byId.isEmpty()){
            logger.error(String.format(ExceptionMsg.NOT_FOUND_MESSAGE, "Training", id));
            throw new NotFoundException(String.format(ExceptionMsg.NOT_FOUND_MESSAGE, "Training", id));
        }
        return byId;
    }

    @Override
    public Training save(Training entity) {
        // Validate entity
        boolean isValid = trainingValidator.isValid(entity);
        if (!isValid){
            logger.error(String.format(ExceptionMsg.ILLIGAL_ARGUMENT_MESSAGE, "Training"));
            throw new IllegalArgumentException(String.format(ExceptionMsg.ILLIGAL_ARGUMENT_MESSAGE, "Training"));
        }
        return trainingRepository.save(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        logger.info("Delete training is not supported yet");
        throw new UnsupportedOperationException("Delete training is not supported yet");
    }
}
