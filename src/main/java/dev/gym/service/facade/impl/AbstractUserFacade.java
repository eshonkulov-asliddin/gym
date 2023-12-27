package dev.gym.service.facade.impl;

import dev.gym.repository.model.User;
import dev.gym.security.authentication.UserAuthService;
import dev.gym.service.UserService;
import dev.gym.service.exception.InvalidUsernameOrPasswordException;
import dev.gym.service.facade.UserFacade;

import java.util.List;
import java.util.Optional;

public abstract class AbstractUserFacade<T, K, E extends User> implements UserFacade<T, K> {

    private final UserAuthService authService;

    private final UserService<T, K, E> userService;

    protected AbstractUserFacade(UserAuthService authService, UserService<T, K, E> userService) {
        this.authService = authService;
        this.userService = userService;
    }


    @Override
    public List<T> getAll(String username, String password) throws InvalidUsernameOrPasswordException {
        authService.authenticate(username, password);
        return userService.getAll();
    }

    @Override
    public Optional<T> getById(K id, String username, String password) {
        authService.authenticate(username, password);
        return userService.getById(id);
    }

    @Override
    public void save(T request) {
        userService.save((E) request);
    }

    @Override
    public boolean deleteById(K id, String username, String password) {
        authService.authenticate(username, password);
        return userService.deleteById(id);
    }

    @Override
    public Optional<T> getByUsername(String username, String password) {
        authService.authenticate(username, password);
        return userService.getByUsername(username);
    }

    @Override
    public void deleteByUsername(String username, String password) {
        authService.authenticate(username, password);
        userService.deleteByUsername(username);
    }

    @Override
    public void updatePassword(String username, String oldPassword,  String newPassword) {
        authService.authenticate(username, oldPassword);
        userService.updatePassword(username, newPassword);
    }

    @Override
    public void setActiveStatus(String username, String password, boolean activeStatus) {
        authService.authenticate(username, password);
        userService.setActiveStatus(username, activeStatus);
    }


    @Override
    public void login(String username, String password) {
        authService.authenticate(username, password);
    }

}
