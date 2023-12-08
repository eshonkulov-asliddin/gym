package dev.gym.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Trainee trainee;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Trainer trainer;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private TrainingType trainingType;

    @Column(name = "training_name", nullable = false)
    private String trainingName;

    @Column(name = "training_date", nullable = false)
    private LocalDate trainingDate;

    @Column(name = "training_duration", nullable = false)
    private int trainingDuration;

}