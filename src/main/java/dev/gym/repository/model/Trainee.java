package dev.gym.repository.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Trainee extends User {

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Training> trainings = new ArrayList<>();

    @ManyToMany(mappedBy = "trainees", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Trainer> trainers = new HashSet<>();

    public void addTraining(Training training) {
        trainings.add(training);
        training.setTrainee(this);
    }

    public void removeTraining(Training training) {
        training.setTrainee(null);
        trainings.remove(training);
    }


    public void addTrainers(List<Trainer> trainers) {
        for (Trainer trainer : trainers){
            this.trainers.add(trainer);
            trainer.getTrainees().add(this);
        }
    }

    public void update(Trainee trainee) {
        super.update(trainee);
        this.dateOfBirth = trainee.dateOfBirth;
        this.address = trainee.address;
    }
}
