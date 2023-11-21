package dev.gym.service.validator;

public interface Validator<T> {
    boolean isValid(T entity);
}
