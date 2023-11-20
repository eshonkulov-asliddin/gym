package dev.gym.repository.datasource.reader.impl;

import dev.gym.model.Specialization;
import dev.gym.model.Trainer;
import dev.gym.repository.datasource.credential.impl.SimpleCredentialGenerator;
import dev.gym.repository.datasource.reader.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TrainerReaderImpl implements Reader<Trainer> {
    private static final Logger logger = LoggerFactory.getLogger(TrainerReaderImpl.class);

    @Value("${trainers.filepath}")
    private String fileName;
    private final SimpleCredentialGenerator simpleCredentialGenerator;

    public TrainerReaderImpl(SimpleCredentialGenerator simpleCredentialGenerator) {
        this.simpleCredentialGenerator = simpleCredentialGenerator;
    }

    @Override
    public List<Trainer> read() {

        logger.info(String.format(Utils.READ_DATA_FROM_FILE, "trainers", fileName));

        List<Trainer> trainerList = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while((line = reader.readLine()) != null){
                Trainer trainer = parse(line);
                trainerList.add(trainer);
            }
        }catch (IOException e){
            logger.error(String.format(Utils.ERROR_READING_FILE, fileName));
            e.printStackTrace();
        }
        return trainerList;
    }

    private Trainer parse(String line) {

        String[] fields = line.split(" ");

        final String FIRSTNAME = fields[0];
        final String LASTNAME = fields[1];
        final boolean IS_ACTIVE = Boolean.parseBoolean(fields[2]);
        final String SPECIALIZATION_TYPE_NAME = fields[3];

        // create specialization
        Specialization specialization = new Specialization(SPECIALIZATION_TYPE_NAME);

        return new Trainer(FIRSTNAME, LASTNAME, simpleCredentialGenerator, IS_ACTIVE, specialization);
    }
}