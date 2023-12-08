package dev.gym.service.facade;

import java.util.Optional;

public interface UserFacade<T, R, K> extends Facade<T, R, K> {

    Optional<R> getByUsername(String username, String password);

    void deleteByUsername(String username, String password);

    void updatePassword(K id, String newPassword, String username, String oldPassword);

    void activate(K id, String username, String password);

    void deactivate(K id, String username, String password);

}