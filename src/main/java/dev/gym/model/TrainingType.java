package dev.gym.model;

import dev.gym.model.enums.TrainingTypeEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @Column(name = "training_type_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private TrainingTypeEnum trainingTypeName;

    @OneToMany(mappedBy = "trainingType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Training> trainingList = new ArrayList<>();

    public void addTraining(Training training) {
        trainingList.add(training);
        training.setTrainingType(this);
    }

    public void removeTraining(Training training) {
        training.setTrainingType(null);
        trainingList.remove(training);
    }
}
