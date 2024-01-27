package dev.gym.security;

import dev.gym.repository.UserRepository;
import dev.gym.repository.model.User;
import dev.gym.security.listener.LoginAttemptService;
import dev.gym.service.exception.AccountBlockedException;
import dev.gym.service.exception.util.ExceptionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository<User, Long> userRepository;
    private final LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (loginAttemptService.isBlocked()){
            throw new AccountBlockedException(ExceptionConstants.ACCOUNT_BLOCKED_MESSAGE);
        }
        return userRepository
                .findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }
}
