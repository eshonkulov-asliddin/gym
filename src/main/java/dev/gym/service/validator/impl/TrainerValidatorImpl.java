package dev.gym.service.validator.impl;

import dev.gym.model.Trainer;
import dev.gym.service.exception.util.ExceptionMsg;
import dev.gym.service.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TrainerValidatorImpl implements Validator<Trainer> {
    private static final Logger logger = LoggerFactory.getLogger(TrainerValidatorImpl.class);
    @Override
    public void validate(Trainer entity) {
        String firstName = entity.getFirstName();
        String lastName = entity.getLastName();

        if ( StringUtils.isBlank(firstName) || StringUtils.isEmpty(firstName) ||
             StringUtils.isBlank(lastName) || StringUtils.isEmpty(lastName) ) {

            logger.error(String.format(ExceptionMsg.ILLIGAL_ARGUMENT_MESSAGE, "Trainer"));
            throw new IllegalArgumentException(String.format(ExceptionMsg.ILLIGAL_ARGUMENT_MESSAGE, "Trainer"));        };

    }
}
