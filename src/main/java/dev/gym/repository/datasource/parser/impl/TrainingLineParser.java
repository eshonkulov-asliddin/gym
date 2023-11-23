    package dev.gym.repository.datasource.parser.impl;

    import dev.gym.model.Trainee;
    import dev.gym.model.Trainer;
    import dev.gym.model.Training;
    import dev.gym.model.TrainingType;
    import dev.gym.repository.datasource.parser.LineParser;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;

    import java.time.LocalDate;
    import java.util.Map;
    import java.util.Random;

    @Component
    public class TrainingLineParser implements LineParser<Training> {
        private final Random random = new Random();
        private final Map<Long, Trainee> traineeStore;
        private final Map<Long, Trainer> trainerStore;

        @Autowired
        public TrainingLineParser(Map<Long, Trainee> traineeStore,
                                  Map<Long, Trainer> trainerStore) {
            this.traineeStore = traineeStore;
            this.trainerStore = trainerStore;
        }

        @Override
        public Training parse(String line) {
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
