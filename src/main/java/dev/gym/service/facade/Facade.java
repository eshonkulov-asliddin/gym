package dev.gym.service.facade;

import java.util.List;
import java.util.Optional;

public interface Facade<T, R, K> {

    List<R> getAll(String username, String password);

    Optional<R> getById(K id, String username, String password);

    R save(T request);

    boolean deleteById(K id, String username, String password);

}
