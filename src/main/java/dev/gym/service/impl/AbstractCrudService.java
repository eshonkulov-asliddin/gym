package dev.gym.service.impl;

import dev.gym.repository.CrudRepository;
import dev.gym.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
abstract class AbstractCrudService<T, K, E> implements CrudService<T, K, E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCrudService.class);
    protected Class<E> entityClass;
    private final CrudRepository<E, K> repository;
    private final ConversionService conversionService;

    protected AbstractCrudService(CrudRepository<E, K> repository, ConversionService conversionService) {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[2];
        this.repository = repository;
        this.conversionService = conversionService;
    }

    @Override
    public List<T> getAll() {
        return repository.findAll().stream()
                .map(entity -> conversionService.convert(entity, getDtoClass()))
                .toList();
    }

    @Override
    public Optional<T> getById(K id) {
        return Optional.ofNullable(id)
                .flatMap(repository::findById)
                .map(entity -> conversionService.convert(entity, getDtoClass()))
                .or(() -> handleNotFound(id));
    }

    @Override
    public void save(E entity) {
        repository.save(entity);
    }

    @Override
    public boolean deleteById(K id) {
        return Optional.ofNullable(id)
                .filter(repository::existById)
                .map(repository::delete)
                .orElse(false);
    }

    private Optional<T> handleNotFound(K id) {
        LOGGER.error("{} with id {} not found", entityClass.getSimpleName(), id);
        return Optional.empty();
    }

    protected abstract Class<T> getDtoClass();
}
