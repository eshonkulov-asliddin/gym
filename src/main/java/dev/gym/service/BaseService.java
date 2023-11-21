package dev.gym.service;

import java.util.Map;
import java.util.Optional;

public interface BaseService<T, K> {
    Map<K, T> findAll();
    Optional<T> findById(K id);
    T save(T entity);
    boolean deleteById(K id);
}
