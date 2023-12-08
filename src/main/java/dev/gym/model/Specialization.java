package dev.gym.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "specialization_type_name", nullable = false)
    private String specializationTypeName;

    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trainer> trainerList = new ArrayList<>();


    public void addTrainer(Trainer trainer) {
        trainerList.add(trainer);
        trainer.setSpecialization(this);
    }

    public void removeTrainer(Trainer trainer) {
        trainer.setSpecialization(null);
        trainerList.remove(trainer);
    }

}