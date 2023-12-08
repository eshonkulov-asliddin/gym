package dev.gym.service.impl;

import dev.gym.model.Trainer;
import dev.gym.model.Training;
import dev.gym.repository.impl.AbstractUserRepository;
import dev.gym.service.dto.TrainerDtoRequest;
import dev.gym.service.dto.TrainerDtoResponse;
import dev.gym.service.mapper.UserMapper;
import dev.gym.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl extends AbstractUserService<TrainerDtoRequest, TrainerDtoResponse, Long, Trainer, Training> {

    @Autowired
    protected TrainerServiceImpl(AbstractUserRepository<Trainer, Training, Long> userRepository,
                                 Validator<TrainerDtoRequest> traineeValidator,
                                 UserMapper<TrainerDtoRequest, TrainerDtoResponse, Trainer> mapper) {

        super(userRepository, traineeValidator, mapper);

    }
}