package dev.gym.service.impl;

import dev.gym.repository.CrudRepository;
import dev.gym.service.CrudService;
import dev.gym.service.exception.util.ExceptionConstants;
import dev.gym.service.mapper.BaseMapper;
import dev.gym.service.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class AbstractCrudService<T, R, K, L> implements CrudService<T, R, K> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractCrudService.class);

    private final CrudRepository<L, K> repository;

    private final Validator<T> validator;

    protected final BaseMapper<T, R, L> mapper;

    public AbstractCrudService(CrudRepository<L, K> repository, Validator<T> validator, BaseMapper<T, R, L> mapper) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public List<R> getAll() {
        return repository.findAll().stream()
                .map(mapper::modelToDto)
                .toList();
    }

    @Override
    public Optional<R> getById(K id) {
        return Optional.ofNullable(id)
                .flatMap(repository::findById)
                .map(mapper::modelToDto)
                .or(() -> handleNotFound(id));
    }

    @Override
    public R save(T request) {
        validator.validate(request);
        return Optional.of(request)
                .map(mapper::dtoToModel)
                .map(repository::save)
                .map(mapper::modelToDto)
                .orElseThrow();
    }

    @Override
    public boolean deleteById(K id) {
        return Optional.ofNullable(id)
                .filter(repository::existById)
                .map(repository::delete)
                .orElse(false);
    }

    private Optional<R> handleNotFound(K id) {
        logger.error(String.format(ExceptionConstants.NOT_FOUND_MESSAGE, "entity", id));
        return Optional.empty();
    }

}
