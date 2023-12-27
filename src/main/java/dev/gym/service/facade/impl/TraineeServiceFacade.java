package dev.gym.service.facade.impl;

import dev.gym.repository.model.Trainee;
import dev.gym.security.authentication.UserAuthService;
import dev.gym.service.UserService;
import dev.gym.service.dto.TraineeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeServiceFacade extends AbstractUserFacade<TraineeDto, Long, Trainee> {

    @Autowired
    public TraineeServiceFacade(UserAuthService authService, UserService<TraineeDto, Long, Trainee> userService) {
        super(authService, userService);
    }
}
