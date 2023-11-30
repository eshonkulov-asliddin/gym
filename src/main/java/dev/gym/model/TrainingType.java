package dev.gym.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class TrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "training_type_name")
    private String trainingTypeName;

    @OneToMany(
            mappedBy = "trainingType",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Training> trainingList = new ArrayList<>();

    public void addTraining(Training training){
        trainingList.add(training);
        training.setTrainingType(this);
    }

    public void removeTraining(Training training){
        trainingList.remove(training);
        training.setTrainingType(null);
    }
}
