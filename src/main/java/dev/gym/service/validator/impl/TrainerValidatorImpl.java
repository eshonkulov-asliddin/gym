package dev.gym.service.validator.impl;

import dev.gym.model.Trainer;
import dev.gym.service.validator.Validator;
import org.springframework.stereotype.Component;

@Component
public class TrainerValidatorImpl implements Validator<Trainer> {
    @Override
    public boolean isValid(Trainer entity) {
        return (entity.getFirstName() != null && !entity.getFirstName().isEmpty()) &&
                (entity.getLastName() != null && !entity.getLastName().isEmpty());
    }
}
