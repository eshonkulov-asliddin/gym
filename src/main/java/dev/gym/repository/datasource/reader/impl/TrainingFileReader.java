package dev.gym.repository.datasource.reader.impl;

import dev.gym.model.Training;
import dev.gym.repository.datasource.parser.LineParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TrainingFileReader extends AbstractFileReader<Training> {
    @Autowired
    public TrainingFileReader(LineParser<Training> lineParser,
                              @Value("${trainings.filepath}") String fileName) {
        super(lineParser, fileName);
    }
}
