package dev.gym.repository.impl;

import dev.gym.repository.TrainingRepository;
import dev.gym.repository.model.Training;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingRepositoryImpl extends AbstractCrudRepository<Training, Long> implements TrainingRepository {

    @Autowired
    public TrainingRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }
}
