package dev.gym.service.facade.impl;

import dev.gym.model.Trainer;
import dev.gym.model.Training;
import dev.gym.service.UserService;
import dev.gym.service.authentication.AuthService;
import dev.gym.service.dto.TrainerDtoRequest;
import dev.gym.service.dto.TrainerDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceFacade extends AbstractUserFacade<TrainerDtoRequest, TrainerDtoResponse, Long, Trainer, Training> {

    @Autowired
    protected TrainerServiceFacade(AuthService<Trainer> authService,
                                   UserService<TrainerDtoRequest, TrainerDtoResponse, Long, Training> trainerService) {

        super(authService, trainerService);

    }

}