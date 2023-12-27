package dev.gym.service.converter.trainee;

import dev.gym.repository.model.Training;
import dev.gym.service.dto.TraineeTrainingDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TrainingToTraineeTrainingDto implements Converter<Training, TraineeTrainingDto> {
    @Override
    public TraineeTrainingDto convert(Training source) {
        return new TraineeTrainingDto(
                source.getTrainingName(),
                source.getTrainingDate(),
                source.getTrainingType().getTrainingTypeName(),
                source.getTrainingDuration(),
                source.getTrainer().getUsername());
    }
}
