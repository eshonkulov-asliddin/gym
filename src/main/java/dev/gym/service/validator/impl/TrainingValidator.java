package dev.gym.service.validator.impl;

import dev.gym.service.dto.TrainingDtoRequest;
import dev.gym.service.dto.TrainingTypeDto;
import dev.gym.service.exception.util.ExceptionConstants;
import dev.gym.service.validator.Validator;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TrainingValidator implements Validator<TrainingDtoRequest> {

    private final Logger logger = LoggerFactory.getLogger(TrainingValidator.class);

    @Override
    public void validate(TrainingDtoRequest request) {
        Long traineeId = request.traineeId();
        Long trainerId = request.trainerId();
        String trainingName = request.trainingName();
        TrainingTypeDto trainingType = request.trainingType();
        LocalDate trainingDate = request.trainingDate();
        int trainingDuration = request.trainingDuration();

        if (ObjectUtils.anyNull(trainerId) || ObjectUtils.anyNull(traineeId) ||
                StringUtils.isBlank(trainingName) || StringUtils.isEmpty(trainingName) ||
                ObjectUtils.isEmpty(trainingType) || ObjectUtils.allNull(trainingDate) || trainingDuration <= 0) {

            String msg = String.format(ExceptionConstants.ILLIGAL_ARGUMENT_MESSAGE, "Training");
            logger.error(msg);
            throw new IllegalArgumentException(msg);

        }
    }
}
