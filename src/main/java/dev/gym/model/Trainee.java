package dev.gym.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Trainee extends User {

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @OneToMany(
            mappedBy = "trainee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Training> trainingList = new ArrayList<>();

    public void addTraining(Training training){
        trainingList.add(training);
        training.setTrainee(this);
    }

    public void removeTraining(Training training){
        trainingList.remove(training);
        training.setTrainee(null);
    }
}
