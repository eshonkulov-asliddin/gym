package dev.gym.repository.datasource.parser;

public interface LineParser<T> {
    T parse(String line);
}
