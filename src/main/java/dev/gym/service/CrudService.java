package dev.gym.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, K, E> {

    List<T> getAll();

    Optional<T> getById(K id);

    void save(E entity);

    boolean deleteById(K id);

}
