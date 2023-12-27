package dev.gym.service.converter.training;

import dev.gym.repository.model.Training;
import dev.gym.service.dto.AddTrainingDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateTrainingDtoToTrainingConverter implements Converter<AddTrainingDto, Training> {
    @Override
    public Training convert(AddTrainingDto source) {
        Training training = new Training();
        training.setTrainingName(source.trainingName());
//        training.setTrainingType(TrainingTypeEnum.STRENGTH); // ask from mentor
        training.setTrainingDate(source.trainingDate());
        training.setTrainingDuration(source.trainingDuration());
        return training;
    }
}
