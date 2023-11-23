package dev.gym.repository.datasource.reader.impl;

import dev.gym.repository.datasource.parser.LineParser;
import dev.gym.repository.datasource.reader.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFileReader<T> implements FileReader<T> {
    private final LineParser<T> lineParser;
    private final String fileName;
    private static final Logger logger = LoggerFactory.getLogger(AbstractFileReader.class);
    AbstractFileReader(LineParser<T> lineParser,
                       String fileName) {
        this.lineParser = lineParser;
        this.fileName = fileName;
    }
    @Override
    public List<T> read() {

        List<T> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T entity = lineParser.parse(line);
                list.add(entity);
            }
        } catch (Exception e) {
            logger.error(String.format(Constants.ERROR_READING_FILE, fileName));
            e.printStackTrace();
        }
        return list;
    }
}
