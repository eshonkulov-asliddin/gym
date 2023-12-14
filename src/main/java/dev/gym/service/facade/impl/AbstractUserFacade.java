package dev.gym.service.facade.impl;

import dev.gym.service.UserService;
import dev.gym.service.authentication.AuthService;
import dev.gym.service.exception.InvalidUsernameOrPasswordException;
import dev.gym.service.facade.UserFacade;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public abstract class AbstractUserFacade<T, R, K, E, L> implements UserFacade<T, R, K> {

    private final AuthService<E> authService;

    private final UserService<T, R, K, L> userService;

    protected AbstractUserFacade(AuthService<E> authService, UserService<T, R, K, L> userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Override
    public List<R> getAll(String username, String password) throws InvalidUsernameOrPasswordException {
        authService.authenticate(username, password);
        return userService.getAll();
    }

    @Override
    public Optional<R> getById(K id, String username, String password) {
        authService.authenticate(username, password);
        return userService.getById(id);
    }

    @Override
    public R save(T request) {
        return userService.save(request);
    }

    @Override
    public boolean deleteById(K id, String username, String password) {
        authService.authenticate(username, password);
        return userService.deleteById(id);
    }

    @Override
    public Optional<R> getByUsername(String username, String password) {
        authService.authenticate(username, password);
        return userService.getByUsername(username);
    }

    @Override
    public void deleteByUsername(String username, String password) {
        authService.authenticate(username, password);
        userService.deleteByUsername(username);
    }

    @Override
    public void updatePassword(K id, String newPassword, String username, String oldPassword) {
        authService.authenticate(username, oldPassword);
        userService.updatePassword(id, newPassword);
    }

    @Override
    public void activate(K id, String username, String password) {
        authService.authenticate(username, password);
        userService.activate(id);
    }

    @Override
    public void deactivate(K id, String username, String password) {
        authService.authenticate(username, password);
        userService.deactivate(id);
    }

}
