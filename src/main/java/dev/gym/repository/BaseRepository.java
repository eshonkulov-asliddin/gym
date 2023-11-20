package dev.gym.repository;

import java.util.Map;
import java.util.Optional;

public interface BaseRepository<T, K> {
    Map<K, T> findAll();
    Optional<T> findById(K id);
    T save(T entity);
    boolean deleteById(K id);
}
