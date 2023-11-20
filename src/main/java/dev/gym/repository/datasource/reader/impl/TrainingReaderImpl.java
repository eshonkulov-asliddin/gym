package dev.gym.repository.datasource.reader.impl;

import dev.gym.model.Trainee;
import dev.gym.model.Trainer;
import dev.gym.model.Training;
import dev.gym.model.TrainingType;
import dev.gym.repository.datasource.reader.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class TrainingReaderImpl implements Reader<Training> {
    private static final Logger logger = LoggerFactory.getLogger(TrainingReaderImpl.class);

    @Value("${trainings.filepath}")
    private String fileName;
    private final Random random = new Random();
    private final Map<Long, Trainee> traineeStore;
    private final Map<Long, Trainer> trainerStore;

    @Autowired
    public TrainingReaderImpl(Map<Long, Trainee> traineeStore,
                              Map<Long, Trainer> trainerStore) {
        this.traineeStore = traineeStore;
        this.trainerStore = trainerStore;
    }

    @Override
    public List<Training> read() {

        logger.info(String.format(Utils.READ_DATA_FROM_FILE, "trainings", fileName));

        List<Training> trainingList = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while((line = reader.readLine()) != null){
                Training training = parse(line);
                trainingList.add(training);
            }

        }catch (IOException e){
            logger.error(String.format(Utils.ERROR_READING_FILE, fileName));
            e.printStackTrace();
        }
        return trainingList;
    }

    private Training parse(String line){
        String[] fields = line.split(" ");

        // get random trainee and trainer
        final Trainee trainee = traineeStore.get(random.nextLong(traineeStore.size()));
        final Trainer trainer = trainerStore.get(random.nextLong(trainerStore.size()));

        final String trainingName = fields[0];

        // create trainingType
        final TrainingType trainingType = new TrainingType(fields[1].trim());
        final LocalDate trainingDate = LocalDate.parse(fields[2].trim());
        final int trainingDuration = Integer.parseInt(fields[3].trim());

        return new Training(trainee, trainer, trainingName, trainingType, trainingDate, trainingDuration);
    }
}
