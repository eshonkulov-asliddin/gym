package dev.gym.service.validator;

public interface Validator<T> {

    void validate(T request);

}
