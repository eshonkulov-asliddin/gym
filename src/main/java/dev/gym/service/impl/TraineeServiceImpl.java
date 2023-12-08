package dev.gym.service.impl;

import dev.gym.model.Trainee;
import dev.gym.model.Training;
import dev.gym.repository.impl.AbstractUserRepository;
import dev.gym.service.dto.TraineeDtoRequest;
import dev.gym.service.dto.TraineeDtoResponse;
import dev.gym.service.mapper.UserMapper;
import dev.gym.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeServiceImpl extends AbstractUserService<TraineeDtoRequest, TraineeDtoResponse, Long, Trainee, Training> {

    @Autowired
    protected TraineeServiceImpl(AbstractUserRepository<Trainee, Training, Long> userRepository,
                                 Validator<TraineeDtoRequest> traineeValidator,
                                 UserMapper<TraineeDtoRequest, TraineeDtoResponse, Trainee> mapper) {

        super(userRepository, traineeValidator, mapper);

    }
}