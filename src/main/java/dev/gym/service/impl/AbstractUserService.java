package dev.gym.service.impl;

import dev.gym.model.User;
import dev.gym.repository.impl.AbstractUserRepository;
import dev.gym.service.UserService;
import dev.gym.service.mapper.UserMapper;
import dev.gym.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public abstract class AbstractUserService<T, R, K, M extends User, L> extends AbstractCrudService<T, R, K, M> implements UserService<T, R, K, L> {

    private final AbstractUserRepository<M, L, K> userRepository;


    @Autowired
    protected AbstractUserService(AbstractUserRepository<M, L, K> userRepository, Validator<T> userValidator, UserMapper<T, R, M> mapper) {
        super(userRepository, userValidator, mapper);
        this.userRepository = userRepository;
    }


    @Override
    public Optional<R> getByUsername(String username) {
        return userRepository.findByUsername(username).map(mapper::modelToDto);
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

}

