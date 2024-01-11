package dev.gym.service.converter.trainer;

import dev.gym.repository.model.Training;
import dev.gym.service.dto.TrainerTrainingDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TrainingToTrainerTrainingDto implements Converter<Training, TrainerTrainingDto> {

    @Override
    public TrainerTrainingDto convert(Training source) {
        return new TrainerTrainingDto(
                source.getTrainingName(),
                source.getTrainingDate(),
                source.getTrainingType().getTrainingTypeName(),
                source.getTrainingDuration(),
                source.getTrainee().getUsername()
        );
    }
}
