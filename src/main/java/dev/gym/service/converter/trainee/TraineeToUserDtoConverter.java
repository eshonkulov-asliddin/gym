package dev.gym.service.converter.trainee;

import dev.gym.repository.model.Trainee;
import dev.gym.service.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TraineeToUserDtoConverter implements Converter<Trainee, UserDto> {

    @Override
    public UserDto convert(Trainee source) {
        return new UserDto(source.getUsername(), source.getPassword());
    }
}
