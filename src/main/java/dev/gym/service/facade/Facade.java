package dev.gym.service.facade;

import java.util.List;
import java.util.Optional;

public interface Facade<T, K> {

    List<T> getAll(String username, String password);

    Optional<T> getById(K id, String username, String password);

    void save(T request);

    boolean deleteById(K id, String username, String password);

}
