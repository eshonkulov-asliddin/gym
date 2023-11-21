package dev.gym.service.validator.impl;

import dev.gym.model.Training;
import dev.gym.service.validator.Validator;
import org.springframework.stereotype.Component;

@Component
public class TrainingValidatorImpl implements Validator<Training> {
    @Override
    public boolean isValid(Training entity) {
        return (entity.getTrainer() != null &&
                entity.getTrainee() != null &&
                entity.getTrainingName() != null &&
                entity.getTrainingType() != null &&
                entity.getTrainingDate() != null &&
                entity.getTrainingDuration() != 0);
    }
}
