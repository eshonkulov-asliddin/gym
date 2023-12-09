package dev.gym.service;

import java.util.Map;
import java.util.Optional;

public interface BaseService<T, K> {
    Map<K, T> getAll();
    Optional<T> getById(K id);
    T save(T entity);
    boolean deleteById(K id);
}
