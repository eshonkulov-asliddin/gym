package dev.gym.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
public class Trainer extends User {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private TrainingType specialization;

    @OneToMany(mappedBy = "trainer", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Training> trainingList = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "Trainer_Trainee", joinColumns = @JoinColumn(name = "trainer_id"), inverseJoinColumns = @JoinColumn(name = "trainee_id"))
    private Set<Trainee> trainees = new HashSet<>();

    public void addTraining(Training training) {
        trainingList.add(training);
        training.setTrainer(this);
    }

    public void removeTraining(Training training) {
        training.setTrainer(null);
        trainingList.remove(training);
    }
}
