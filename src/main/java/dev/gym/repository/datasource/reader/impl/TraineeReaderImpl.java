package dev.gym.repository.datasource.reader.impl;

import dev.gym.model.Trainee;
import dev.gym.repository.datasource.credential.impl.SimpleCredentialGenerator;
import dev.gym.repository.datasource.reader.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class TraineeReaderImpl implements Reader<Trainee> {
    private static final Logger logger = LoggerFactory.getLogger(TraineeReaderImpl.class);

    @Value("${trainees.filepath}")
    private String fileName;
    private final SimpleCredentialGenerator simpleCredentialGenerator;

    public TraineeReaderImpl(SimpleCredentialGenerator simpleCredentialGenerator) {
        this.simpleCredentialGenerator = simpleCredentialGenerator;
    }

    @Override
    public List<Trainee> read() {

        logger.info(String.format(Utils.READ_DATA_FROM_FILE, "trainees", fileName));

        List<Trainee> traineeList = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = reader.readLine()) != null){
                Trainee trainee = parse(line);
                traineeList.add(trainee);
            }
        } catch (Exception e) {
            logger.error(String.format(Utils.ERROR_READING_FILE, fileName));
            e.printStackTrace();
        }
        return traineeList;
    }

    private Trainee parse(String line) {

        String[] fields = line.split(" ");

        final String FIRSTNAME = fields[0];
        final String LASTNAME = fields[1];
        final boolean IS_ACTIVE = Boolean.parseBoolean(fields[2]);

        Trainee.Builder builder = new Trainee.Builder(FIRSTNAME, LASTNAME, simpleCredentialGenerator, IS_ACTIVE);

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
}
