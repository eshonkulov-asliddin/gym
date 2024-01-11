package dev.gym.service.converter.trainer;

import dev.gym.repository.model.Trainer;
import dev.gym.service.dto.UpdateTrainerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateTrainerDtoToTrainerConverter implements Converter<UpdateTrainerDto, Trainer> {

    @Override
    public Trainer convert(UpdateTrainerDto source) {
        Trainer trainer = new Trainer();
        trainer.setFirstName(source.firstName());
        trainer.setLastName(source.lastName());
        trainer.setActive(source.isActive());
        return trainer;
    }
}
