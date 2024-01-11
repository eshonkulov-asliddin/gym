package dev.gym.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, K> {

    List<T> findAll();

    Optional<T> findById(K id);

    void save(T entity);

    boolean delete(K id);

    boolean existById(K id);

}
