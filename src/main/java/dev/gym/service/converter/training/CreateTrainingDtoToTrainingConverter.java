package dev.gym.service.converter.training;

import dev.gym.repository.model.Training;
import dev.gym.service.dto.CreateTrainingDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateTrainingDtoToTrainingConverter implements Converter<CreateTrainingDto, Training> {

    @Override
    public Training convert(CreateTrainingDto source) {
        Training training = new Training();
        training.setTrainingName(source.trainingName());
        training.setTrainingDate(source.trainingDate());
        training.setTrainingDuration(source.trainingDuration());
        return training;
    }
}
