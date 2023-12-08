package dev.gym.service;

import java.util.List;
import java.util.Optional;

public interface UserService<T, R, K, L> extends CrudService<T, R, K> {

    Optional<R> getByUsername(String username);

    void deleteByUsername(String username);

    void updatePassword(K id, String newPassword);

    void activate(K id);

    void deactivate(K id);

    List<L> getAllTrainingsByUsername(String username);

}