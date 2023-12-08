package dev.gym.repository.impl;

import dev.gym.model.Training;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingRepository extends AbstractCrudRepository<Training, Long> {

    @Autowired
    public TrainingRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

}