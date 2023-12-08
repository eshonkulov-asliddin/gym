package dev.gym.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, R, K> {

    List<R> getAll();

    Optional<R> getById(K id);

    R save(T request);

    boolean deleteById(K id);

}