package dev.gym.service.impl;

import dev.gym.model.Training;
import dev.gym.repository.CrudRepository;
import dev.gym.service.dto.TrainingDtoReponse;
import dev.gym.service.dto.TrainingDtoRequest;
import dev.gym.service.mapper.TrainingMapper;
import dev.gym.service.validator.Validator;
import org.springframework.stereotype.Service;

@Service
public class TrainingService extends AbstractCrudService<TrainingDtoRequest, TrainingDtoReponse, Long, Training> {

    public TrainingService(CrudRepository<Training, Long> trainingRepository,
                           Validator<TrainingDtoRequest> trainingValidator,
                           TrainingMapper mapper) {
        super(trainingRepository, trainingValidator, mapper);
    }

}
