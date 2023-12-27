package dev.gym.service.converter.training;

import dev.gym.repository.model.TrainingType;
import dev.gym.service.dto.TrainingTypeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TrainingTypeToTrainingTypeDtoConverter implements Converter<TrainingType, TrainingTypeDto> {

    @Override
    public TrainingTypeDto convert(TrainingType source) {
        return new TrainingTypeDto(source.getTrainingTypeName().toString(), source.getId());
    }
}
