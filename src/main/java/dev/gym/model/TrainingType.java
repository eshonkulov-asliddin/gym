package dev.gym.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@NoArgsConstructor
public class TrainingType {
    private Long id; //PK
    private String trainingTypeName;

    //ID generator
    private static AtomicLong idGenerator = new AtomicLong(1);

    public TrainingType(String trainingTypeName){
        this.id = idGenerator.getAndIncrement();
        this.trainingTypeName = trainingTypeName;
    }
}
