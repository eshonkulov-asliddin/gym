package dev.gym.service.impl;

import dev.gym.model.Training;
import dev.gym.repository.CrudRepository;
import dev.gym.service.CrudService;
import dev.gym.service.dto.TrainingDtoReponse;
import dev.gym.service.dto.TrainingDtoRequest;
import dev.gym.service.exception.util.ExceptionConstants;
import dev.gym.service.mapper.TrainingMapper;
import dev.gym.service.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements CrudService<TrainingDtoRequest, TrainingDtoReponse, Long> {

    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);

    private final CrudRepository<Training, Long> trainingRepository;

    private final Validator<TrainingDtoRequest> trainingValidator;

    private final TrainingMapper mapper;

    @Autowired
    public TrainingServiceImpl(CrudRepository<Training, Long> trainingRepository,
                               Validator<TrainingDtoRequest> trainingValidator,
                               TrainingMapper mapper) {
        this.trainingRepository = trainingRepository;
        this.trainingValidator = trainingValidator;
        this.mapper = mapper;
    }

    @Override
    public List<TrainingDtoReponse> getAll() {
        return trainingRepository.findAll().stream()
                .map(mapper::modelToDto)
                .toList();
    }

    @Override
    public Optional<TrainingDtoReponse> getById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(trainingRepository::findById)
                .map(mapper::modelToDto)
                .or(() -> handleNotFound(id));
    }

    @Override
    public TrainingDtoReponse save(TrainingDtoRequest request) {
        trainingValidator.validate(request);
        return Optional.of(request)
                .map(mapper::dtoToModel)
                .map(trainingRepository::save)
                .map(mapper::modelToDto)
                .orElseThrow();
    }

    @Override
    public boolean deleteById(Long id) {
        logger.info("Delete training is not supported yet");
        throw new UnsupportedOperationException("Delete training is not supported yet");
    }

    private Optional<TrainingDtoReponse> handleNotFound(Long id) {
        logger.error(String.format(ExceptionConstants.NOT_FOUND_MESSAGE, "Training", id));
        return Optional.empty();
    }

}