package dev.gym.service.authentication;

import dev.gym.model.Trainer;
import dev.gym.model.Training;
import dev.gym.repository.UserRepository;
import dev.gym.service.exception.InvalidUsernameOrPasswordException;
import dev.gym.service.exception.util.ExceptionConstants;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Component;

@Component
public class TrainerAuthService implements AuthService<Trainer> {

    private final UserRepository<Trainer, Training, Long> trainerRepository;

    public TrainerAuthService(UserRepository<Trainer, Training, Long> trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public void authenticate(String username, String password) {
        try {
            trainerRepository.findByUsername(username)
                    .filter(trainer -> trainer.getPassword().equals(password))
                    .orElseThrow(() -> new InvalidUsernameOrPasswordException(ExceptionConstants.INVALID_USERNAME_OR_PASSWORD_MESSAGE));
        } catch (NoResultException e){
            throw new InvalidUsernameOrPasswordException(ExceptionConstants.INVALID_USERNAME_OR_PASSWORD_MESSAGE);
        }
    }

}
