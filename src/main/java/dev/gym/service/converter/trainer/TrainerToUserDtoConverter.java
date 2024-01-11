package dev.gym.service.converter.trainer;

import dev.gym.repository.model.Trainer;
import dev.gym.service.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TrainerToUserDtoConverter implements Converter<Trainer, UserDto> {
    @Override
    public UserDto convert(Trainer source) {
        return new UserDto(source.getUsername(), source.getPassword());
    }
}
