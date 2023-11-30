package dev.gym.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Specialization{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "specialization_type_name")
    private String specializationTypeName;

    @OneToMany(
            mappedBy = "specialization",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Trainer> trainerList = new ArrayList<>();


    public void addTrainer(Trainer trainer){
        trainerList.add(trainer);
        trainer.setSpecialization(this);
    }

    public void removeTrainer(Trainer trainer){
        trainerList.remove(trainer);
        trainer.setSpecialization(null);
    }
}
