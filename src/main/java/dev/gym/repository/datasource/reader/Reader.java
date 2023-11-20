package dev.gym.repository.datasource.reader;

import java.util.List;

public interface Reader<T> {
    List<T> read();
}
