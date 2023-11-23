package dev.gym.service.impl;

import dev.gym.model.Trainee;
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
public class TraineeService implements BaseService<Trainee, Long> {
    private static final Logger logger = LoggerFactory.getLogger(TraineeService.class);
    private final BaseRepository<Trainee, Long> traineeRepository;
    private final Validator<Trainee> traineeValidator;

    @Autowired
    public TraineeService(BaseRepository<Trainee, Long> traineeRepository,
                          Validator<Trainee> traineeValidator) {
        this.traineeRepository = traineeRepository;
        this.traineeValidator = traineeValidator;
    }
    @Override
    public Map<Long, Trainee> getAll() {
        return traineeRepository.findAll();
    }

    @Override
    public Optional<Trainee> getById(Long id) {
        Optional<Trainee> trainee = traineeRepository.findById(id);
        if (trainee.isEmpty()){
            logger.error(String.format(ExceptionMsg.NOT_FOUND_MESSAGE, "Trainee", id));
            return Optional.empty();
        }
        return trainee;
    }

    @Override
    public Trainee save(Trainee entity) {
        // Validate entity
        traineeValidator.validate (entity);

        return traineeRepository.save(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        if( getById(id).isEmpty() ){
            logger.error(String.format(ExceptionMsg.NOT_FOUND_MESSAGE, "Trainee", id));
            throw new NotFoundException(String.format(ExceptionMsg.NOT_FOUND_MESSAGE, "Trainee", id));
        }
        return true;
    }
}
