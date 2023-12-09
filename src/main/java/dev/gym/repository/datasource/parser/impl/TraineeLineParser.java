package dev.gym.repository.datasource.parser.impl;

import dev.gym.model.Trainee;
import dev.gym.repository.datasource.credential.impl.SimpleCredentialGenerator;
import dev.gym.repository.datasource.parser.LineParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TraineeLineParser implements LineParser<Trainee> {
    private SimpleCredentialGenerator simpleCredentialGenerator;

    @Override
    public Trainee parse(String line) {

        String[] fields = line.split(" ");

        final String FIRSTNAME = fields[0];
        final String LASTNAME = fields[1];
        final boolean IS_ACTIVE = Boolean.parseBoolean(fields[2]);

        Trainee.TraineeBuilder builder;
        builder = Trainee.builder();
        builder.firstName(FIRSTNAME);
        builder.lastName(LASTNAME);
        builder.isActive(IS_ACTIVE);
        builder.credentialGenerator(simpleCredentialGenerator);
        // Check if dateOfBirth field is present
        if (fields.length > 3 && !fields[3].trim().isEmpty()) {
            builder.dateOfBirth(LocalDate.parse(fields[3].trim()));
        }

        // Check if Address field is present
        if (fields.length > 4 && !fields[4].trim().isEmpty()) {
            builder.address(fields[4].trim());
        }
        return builder.build();
    }
    @Autowired
    public void setSimpleCredentialGenerator(SimpleCredentialGenerator simpleCredentialGenerator) {
        this.simpleCredentialGenerator = simpleCredentialGenerator;
    }
}
