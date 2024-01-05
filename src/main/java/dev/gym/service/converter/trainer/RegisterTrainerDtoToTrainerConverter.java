package dev.gym.service.converter.trainer;

import dev.gym.repository.model.Trainer;
import dev.gym.service.dto.RegisterTrainerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterTrainerDtoToTrainerConverter implements Converter<RegisterTrainerDto, Trainer> {

    @Override
    public Trainer convert(RegisterTrainerDto source) {
        Trainer trainer = new Trainer();
        trainer.setFirstName(source.firstName());
        trainer.setLastName(source.lastName());
        return trainer;
    }
}
