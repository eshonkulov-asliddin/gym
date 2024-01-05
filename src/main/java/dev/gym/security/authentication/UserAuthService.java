package dev.gym.security.authentication;

import dev.gym.repository.UserRepository;
import dev.gym.repository.model.User;
import dev.gym.service.exception.InvalidUsernameOrPasswordException;
import dev.gym.service.exception.util.ExceptionConstants;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthService implements AuthService {

    private final UserRepository<User, Long> userRepository;

    @Override
    public void authenticate(String username, String password) throws InvalidUsernameOrPasswordException {
        try {
            userRepository.findByUsername(username)
                    .filter(user -> user.getPassword().equals(password))
                    .orElseThrow(() -> new InvalidUsernameOrPasswordException(ExceptionConstants.INVALID_USERNAME_OR_PASSWORD_MESSAGE));
        } catch (NoResultException e){
            throw new InvalidUsernameOrPasswordException(ExceptionConstants.INVALID_USERNAME_OR_PASSWORD_MESSAGE);
        }
    }
}
