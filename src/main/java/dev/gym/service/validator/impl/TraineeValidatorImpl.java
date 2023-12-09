package dev.gym.service.validator.impl;

import dev.gym.model.Trainee;
import dev.gym.service.exception.util.ExceptionMsg;
import dev.gym.service.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class TraineeValidatorImpl implements Validator<Trainee> {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TraineeValidatorImpl.class);
    @Override
    public void validate(Trainee entity) {
        String firstName = entity.getFirstName();
        String lastName = entity.getLastName();

        if ( StringUtils.isBlank(firstName) || StringUtils.isEmpty(firstName) ||
             StringUtils.isBlank(lastName) || StringUtils.isEmpty(lastName) ) {

            logger.error(String.format(ExceptionMsg.ILLIGAL_ARGUMENT_MESSAGE, "Trainee"));
            throw new IllegalArgumentException(String.format(ExceptionMsg.ILLIGAL_ARGUMENT_MESSAGE, "Trainee"));

        }
    }
}
