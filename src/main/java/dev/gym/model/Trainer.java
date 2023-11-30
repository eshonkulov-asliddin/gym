package dev.gym.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Trainer extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    private Specialization specialization;

    @OneToMany(
            mappedBy = "trainer",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Training> trainingList = new ArrayList<>();

    public void addTraining(Training training){
        trainingList.add(training);
        training.setTrainer(this);
    }
    public void removeTraining(Training training){
        trainingList.remove(training);
        training.setTrainer(null);
    }
}
