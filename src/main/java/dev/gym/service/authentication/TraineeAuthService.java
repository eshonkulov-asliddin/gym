package dev.gym.service.authentication;

import dev.gym.model.Trainee;
import dev.gym.model.Training;
import dev.gym.repository.UserRepository;
import dev.gym.repository.impl.TraineeRepository;
import dev.gym.service.exception.InvalidUsernameOrPasswordException;
import dev.gym.service.exception.util.ExceptionConstants;
import org.springframework.stereotype.Component;

@Component
public class TraineeAuthService implements AuthService<Trainee> {

    private final UserRepository<Trainee, Training, Long> traineeRepository;

    public TraineeAuthService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }

    @Override
    public void authenticate(String username, String password) {
        traineeRepository.findByUsername(username)
                .filter(trainee -> trainee.getPassword().equals(password))
                .orElseThrow(() -> new InvalidUsernameOrPasswordException(ExceptionConstants.INVALID_USERNAME_OR_PASSWORD_MESSAGE));
    }

}