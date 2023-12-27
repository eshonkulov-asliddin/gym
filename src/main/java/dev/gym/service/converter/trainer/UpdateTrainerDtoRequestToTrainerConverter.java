package dev.gym.service.converter.trainer;

import dev.gym.repository.model.Trainer;
import dev.gym.service.dto.UpdateTrainerDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UpdateTrainerDtoRequestToTrainerConverter implements Converter<UpdateTrainerDto, Trainer> {

    @Override
    public Trainer convert(UpdateTrainerDto source) {
        Trainer trainer = new Trainer();
        trainer.setFirstName(source.firstName());
        trainer.setLastName(source.lastName());
        trainer.setSpecialization(source.specialization());
        trainer.setActive(source.isActive());
        return trainer;
    }
}
