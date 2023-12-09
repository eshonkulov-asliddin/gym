package dev.gym.repository.datasource.reader.impl;

import dev.gym.model.Trainer;
import dev.gym.repository.datasource.parser.LineParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TrainerFileReader extends AbstractFileReader<Trainer> {
    public TrainerFileReader(LineParser<Trainer> lineParser,
                             @Value("${trainers.filepath}") String fileName) {
        super(lineParser, fileName);
    }
}