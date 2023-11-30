package dev.gym.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, K> {
    List<T> findAll();
    Optional<T> findById(K id);
    T save(T entity);
    boolean deleteById(K id);
    Optional<T> findProxyById(K id);
}
