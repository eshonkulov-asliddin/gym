package dev.gym.service.facade.impl;

import dev.gym.repository.model.Trainer;
import dev.gym.security.authentication.UserAuthService;
import dev.gym.service.UserService;
import dev.gym.service.dto.TrainerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceFacade extends AbstractUserFacade<TrainerDto, Long, Trainer> {

    @Autowired
    public TrainerServiceFacade(UserAuthService authService, UserService<TrainerDto, Long, Trainer> userService) {
        super(authService, userService);
    }
}
