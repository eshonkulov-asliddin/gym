package dev.gym.repository.impl;

import dev.gym.model.BaseEntity;
import dev.gym.repository.BaseRepository;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Optional;

abstract class AbstractRepository<T extends BaseEntity<K>, K>
        implements BaseRepository<T, K>{
    private final Logger logger;
    private final Map<K, T> data;
    AbstractRepository(Logger logger, final Map<K, T> data){
        this.logger = logger;
        this.data = data;
    }
    @Override
    public Map<K,T> findAll() {
        logger.info("Find all entities");
        return Map.copyOf(data);
    }
    @Override
    public Optional<T> findById(K id) {
        logger.info("Find entity by id {}", id);
        return Optional.ofNullable(this.data.get(id));
    }

    public T save(T entity) {
        K id = entity.getId();
        data.put(id, entity);
        logger.info("Entity saved");
        return entity;
    }

    @Override
    public boolean deleteById(K id) {
        logger.info("Deleting entity by id {}", id);
        return data.remove(id) != null;
    }
}
