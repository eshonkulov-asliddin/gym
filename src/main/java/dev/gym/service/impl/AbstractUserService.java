package dev.gym.service.impl;

import dev.gym.repository.UserRepository;
import dev.gym.repository.model.User;
import dev.gym.service.UserService;
import dev.gym.service.exception.NotFoundException;
import dev.gym.service.exception.util.ExceptionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

abstract class AbstractUserService<T, K, E extends User> extends AbstractCrudService<T, K, E> implements UserService<T, K, E> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractUserService.class);
    protected final UserRepository<E, K> userRepository;
    private final ConversionService conversionService;

    AbstractUserService(UserRepository<E, K> userRepository, ConversionService conversionService) {
        super(userRepository, conversionService);
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }


    @Override
    public Optional<T> getByUsername(String username) {
        checkUserExistence(username);
        return userRepository.findByUsername(username)
                .map(entity -> conversionService.convert(entity, getDtoClass()));
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        checkUserExistence(username);
        userRepository.deleteByUsername(username);
    }

    @Override
    @Transactional
    public void updatePassword(String username, String newPassword) {
        checkUserExistence(username);
        userRepository.updatePasswordByUsername(username, newPassword);
    }

    @Override
    @Transactional
    public void setActiveStatus(String username, boolean activeStatus) {
        checkUserExistence(username);
        userRepository.setActiveStatusByUsername(username, activeStatus);
    }

    private void checkUserExistence(String username) {
        if(!userRepository.existsByUsername(username)) {
            LOGGER.info("{} with username {} not found", entityClass.getSimpleName(), username);
            throw new NotFoundException(
                    String.format(
                            ExceptionConstants.NOT_FOUND_MESSAGE, entityClass.getSimpleName(), username
                    )
            );
        }
    }
}
