package dev.gym.service.validator.impl;

import dev.gym.service.dto.TraineeDtoRequest;
import dev.gym.service.exception.util.ExceptionConstants;
import dev.gym.service.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TraineeValidator implements Validator<TraineeDtoRequest> {

    private final Logger logger = LoggerFactory.getLogger(TraineeValidator.class);

    @Override
    public void validate(TraineeDtoRequest request) {
        String firstName = request.firstName();
        String lastName = request.lastName();

        if (StringUtils.isBlank(firstName) || StringUtils.isEmpty(firstName) ||
                StringUtils.isBlank(lastName) || StringUtils.isEmpty(lastName)) {

            String msg = String.format(ExceptionConstants.ILLIGAL_ARGUMENT_MESSAGE, "Trainee");
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
    }

}
