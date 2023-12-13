package dev.gym.repository.impl;

import dev.gym.model.Trainee;
import dev.gym.model.Training;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TraineeRepository extends AbstractUserRepository<Trainee, Training, Long> {

    public TraineeRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

}