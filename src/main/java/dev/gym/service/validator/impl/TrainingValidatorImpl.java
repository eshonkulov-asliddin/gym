package dev.gym.service.validator.impl;

import dev.gym.model.Trainee;
import dev.gym.model.Trainer;
import dev.gym.model.Training;
import dev.gym.model.TrainingType;
import dev.gym.service.exception.util.ExceptionMsg;
import dev.gym.service.validator.Validator;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TrainingValidatorImpl implements Validator<Training> {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TrainingValidatorImpl.class);
    @Override
    public void validate(Training entity) {
        Trainer trainer = entity.getTrainer();
        Trainee trainee = entity.getTrainee();
        String trainingName = entity.getTrainingName();
        TrainingType trainingType = entity.getTrainingType();
        LocalDate trainingDate = entity.getTrainingDate();
        int trainingDuration = entity.getTrainingDuration();

        if ( ObjectUtils.allNull(trainer) || ObjectUtils.allNull(trainee) ||
             StringUtils.isBlank(trainingName) || StringUtils.isEmpty(trainingName) ||
             ObjectUtils.anyNull(trainingType) || ObjectUtils.allNull(trainingDate) ||
             ObjectUtils.allNull(trainingDuration) ) {

            logger.error(String.format(ExceptionMsg.ILLIGAL_ARGUMENT_MESSAGE, "Training"));
            throw new IllegalArgumentException(String.format(ExceptionMsg.ILLIGAL_ARGUMENT_MESSAGE, "Training"));
        }
    }
}
