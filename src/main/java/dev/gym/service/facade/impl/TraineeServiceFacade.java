package dev.gym.service.facade.impl;

import dev.gym.model.Trainee;
import dev.gym.model.Training;
import dev.gym.service.UserService;
import dev.gym.service.authentication.AuthService;
import dev.gym.service.dto.TraineeDtoRequest;
import dev.gym.service.dto.TraineeDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeServiceFacade extends AbstractUserFacade<TraineeDtoRequest, TraineeDtoResponse, Long, Trainee, Training> {

    @Autowired
    protected TraineeServiceFacade(AuthService<Trainee> authService,
                                   UserService<TraineeDtoRequest, TraineeDtoResponse, Long, Training> traineeService) {

        super(authService, traineeService);

    }

}