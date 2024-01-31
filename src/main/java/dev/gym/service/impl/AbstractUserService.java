package dev.gym.service.impl;

import dev.gym.repository.UserRepository;
import dev.gym.repository.model.User;
import dev.gym.service.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

abstract class AbstractUserService<T, K, E extends User> extends AbstractCrudService<T, K, E> implements UserService<T, K, E> {

    protected final UserRepository<E, K> userRepository;
    private final ConversionService conversionService;

    AbstractUserService(UserRepository<E, K> userRepository, ConversionService conversionService) {
        super(userRepository, conversionService);
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }


    @Override
    public Optional<T> getByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(entity -> conversionService.convert(entity, getDtoClass()));
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    @Transactional
    public void updatePassword(String username, String newPassword) {
        userRepository.updatePasswordByUsername(username, newPassword);
    }

    @Override
    @Transactional
    public void setActiveStatus(String username, boolean activeStatus) {
        userRepository.setActiveStatusByUsername(username, activeStatus);
    }
}
