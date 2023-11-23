package dev.gym.repository.datasource.parser.impl;

import dev.gym.model.Specialization;
import dev.gym.model.Trainer;
import dev.gym.repository.datasource.credential.impl.SimpleCredentialGenerator;
import dev.gym.repository.datasource.parser.LineParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainerLineParser implements LineParser<Trainer> {
    public static final String DELIMITER = " ";
    private SimpleCredentialGenerator simpleCredentialGenerator;

    public Trainer parse(String line) {

        String[] fields = line.split(DELIMITER);

        final String FIRSTNAME = fields[0];
        final String LASTNAME = fields[1];
        final boolean IS_ACTIVE = Boolean.parseBoolean(fields[2]);
        final String SPECIALIZATION_TYPE_NAME = fields[3];

        // create specialization
        Specialization specialization = new Specialization(SPECIALIZATION_TYPE_NAME);

        return new Trainer(FIRSTNAME, LASTNAME, simpleCredentialGenerator, IS_ACTIVE, specialization);
    }
    @Autowired
    public void setSimpleCredentialGenerator(SimpleCredentialGenerator simpleCredentialGenerator) {
        this.simpleCredentialGenerator = simpleCredentialGenerator;
    }
}
