package dev.gym.repository.impl;

import dev.gym.model.Trainer;
import dev.gym.model.Training;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TrainerRepository extends AbstractUserRepository<Trainer, Training, Long> {

    TrainerRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

}