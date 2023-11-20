package dev.gym.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

@Data
@NoArgsConstructor
public class Training implements BaseEntity<Long> {
    private Long id; //PK
    private Trainee trainee; //FK
    private Trainer trainer; //FK
    private String trainingName;
    private TrainingType trainingType; //FK
    private LocalDate trainingDate;
    private int trainingDuration;

    // ID generator
    private static AtomicLong idGenerator = new AtomicLong(1);

    public Training(Trainee trainee,
                    Trainer trainer,
                    String trainingName,
                    TrainingType trainingType,
                    LocalDate trainingDate,
                    int trainingDuration){
        this.id = idGenerator.getAndIncrement();
        this.trainee = trainee;
        this.trainer = trainer;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }
}
