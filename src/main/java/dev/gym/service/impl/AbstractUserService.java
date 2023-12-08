package dev.gym.service.impl;

import dev.gym.model.User;
import dev.gym.repository.impl.AbstractUserRepository;
import dev.gym.service.UserService;
import dev.gym.service.exception.util.ExceptionConstants;
import dev.gym.service.mapper.UserMapper;
import dev.gym.service.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public abstract class AbstractUserService<T, R, K, M extends User, L> implements UserService<T, R, K, L> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractUserService.class);

    private final AbstractUserRepository<M, L, K> userRepository;

    private final Validator<T> userValidator;

    private final UserMapper<T, R, M> mapper;

    @Autowired
    protected AbstractUserService(AbstractUserRepository<M, L, K> userRepository, Validator<T> userValidator, UserMapper<T, R, M> mapper) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.mapper = mapper;
    }


    @Override
    public List<R> getAll() {
        return userRepository.findAll().stream()
                .map(mapper::modelToDto)
                .toList();
    }

    @Override
    public Optional<R> getById(K id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .map(mapper::modelToDto)
                .or(() -> handleNotFound(id));
    }

    @Override
    public R save(T request) {
        userValidator.validate(request);
        return Optional.of(request)
                .map(mapper::dtoToModel)
                .map(model -> {
                    model.setActive(true);
                    return userRepository.save(model);
                })
                .map(mapper::modelToDto)
                .orElseThrow();
    }

    @Override
    public boolean deleteById(K id) {
        return getById(id)
                .map(traineeDtoResponse -> {
                    handleNotFound(id);
                    return false;
                })
                .orElse(true);
    }

    @Override
    public Optional<R> getByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(mapper::modelToDto);
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public void updatePassword(K id, String newPassword) {
        userRepository.updatePassword(id, newPassword);
    }

    @Override
    public void activate(K id) {
        userRepository.setActiveStatus(id, true);
    }

    @Override
    public void deactivate(K id) {
        userRepository.setActiveStatus(id, false);
    }

    @Override
    public List<L> getAllTrainingsByUsername(String username) {
        return userRepository.findAllTrainingsByUsername(username);
    }

    private Optional<R> handleNotFound(K id) {
        logger.error(String.format(ExceptionConstants.NOT_FOUND_MESSAGE, "user", id));
        return Optional.empty();
    }

}