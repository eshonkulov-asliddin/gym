package dev.gym.repository.datasource.reader;

import java.util.List;

public interface FileReader<T> {
    List<T> read();
}
