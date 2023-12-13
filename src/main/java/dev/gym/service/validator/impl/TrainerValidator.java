package dev.gym.service.validator.impl;

import dev.gym.service.dto.TrainerDtoRequest;
import dev.gym.service.dto.TrainingTypeDto;
import dev.gym.service.exception.util.ExceptionConstants;
import dev.gym.service.validator.Validator;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TrainerValidator implements Validator<TrainerDtoRequest> {

    private final Logger logger = LoggerFactory.getLogger(TrainerValidator.class);

    @Override
    public void validate(TrainerDtoRequest request) {
        String firstName = request.firstName();
        String lastName = request.lastName();
        TrainingTypeDto specialization = request.specialization();

        if ( StringUtils.isBlank(firstName) || StringUtils.isEmpty(firstName) ||
                StringUtils.isBlank(lastName) || StringUtils.isEmpty(lastName) ||
                ObjectUtils.anyNull(specialization) || ObjectUtils.isEmpty(specialization) ) {

            logger.error(String.format(ExceptionConstants.ILLIGAL_ARGUMENT_MESSAGE, "Trainer"));
            throw new IllegalArgumentException(String.format(ExceptionConstants.ILLIGAL_ARGUMENT_MESSAGE, "Trainer"));
        }
    }

}