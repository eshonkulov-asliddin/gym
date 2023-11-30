package dev.gym.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, K> {
    List<T> getAll();
    Optional<T> getById(K id);
    T save(T entity);
    boolean deleteById(K id);
}
