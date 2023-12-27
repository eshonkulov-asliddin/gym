package dev.gym.service.impl;

import dev.gym.repository.UserRepository;
import dev.gym.repository.model.User;
import dev.gym.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl extends AbstractUserService<UserDto, Long, User> {

    @Autowired
    public UserServiceImpl(UserRepository<User, Long> userRepository,
                           @Qualifier("customConvertionService") ConversionService conversionService) {
        super(userRepository, conversionService);
    }

    @Override
    public Class<UserDto> getDtoClass() {
        return UserDto.class;
    }
}
