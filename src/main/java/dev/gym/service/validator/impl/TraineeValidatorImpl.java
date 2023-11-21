package dev.gym.service.validator.impl;

import dev.gym.model.Trainee;
import dev.gym.service.validator.Validator;
import org.springframework.stereotype.Component;

@Component
public class TraineeValidatorImpl implements Validator<Trainee> {
    @Override
    public boolean isValid(Trainee entity) {
        return (entity.getFirstName() != null && !entity.getFirstName().isEmpty()) &&
                (entity.getLastName() != null && !entity.getLastName().isEmpty());

    }
}
