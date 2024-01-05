package dev.gym.repository.impl;

import dev.gym.repository.TraineeRepository;
import dev.gym.repository.model.Trainee;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TraineeRepositoryImpl extends AbstractUserRepository<Trainee, Long> implements TraineeRepository {

    @Autowired
    public TraineeRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }
}
