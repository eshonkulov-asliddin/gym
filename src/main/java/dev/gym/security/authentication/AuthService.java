package dev.gym.security.authentication;

import dev.gym.service.exception.InvalidUsernameOrPasswordException;

public interface AuthService {

    void authenticate(String username, String password) throws InvalidUsernameOrPasswordException;

}
