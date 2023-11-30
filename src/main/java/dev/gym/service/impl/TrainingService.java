package dev.gym.service.impl;

import dev.gym.model.Training;
import dev.gym.repository.BaseRepository;
import dev.gym.service.BaseService;
import dev.gym.service.exception.util.ExceptionMsg;
import dev.gym.service.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<Training>  getAll() {
        return trainingRepository.findAll();
    }

    @Override
    public Optional<Training> getById(Long id) {
        Optional<Training> training = trainingRepository.findProxyById(id);
        if ( training.isEmpty() ){
            logger.error(String.format(ExceptionMsg.NOT_FOUND_MESSAGE, "Training", id));
            return Optional.empty();
        }
        return training;
    }

    @Override
    public Training save(Training entity) {
        // Validate entity
        trainingValidator.validate(entity);
        return trainingRepository.save(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        logger.info("Delete training is not supported yet");
        throw new UnsupportedOperationException("Delete training is not supported yet");
    }
}
