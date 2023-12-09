package dev.gym.repository.datasource.reader.impl;

import dev.gym.model.Trainee;
import dev.gym.repository.datasource.parser.LineParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TraineeFileReader extends AbstractFileReader<Trainee> {
    public TraineeFileReader(LineParser<Trainee> lineParser,
                             @Value("${trainees.filepath}") String fileName) {
        super(lineParser, fileName);
    }
}
