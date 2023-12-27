package dev.gym.repository.impl;

import dev.gym.repository.TrainingTypeRepository;
import dev.gym.repository.model.TrainingType;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static dev.gym.repository.impl.util.TransactionUtil.executeInTransaction;

@Repository
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {

    private final EntityManagerFactory entityManagerFactory;

    public TrainingTypeRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<TrainingType> findAll() {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            String query = "SELECT t FROM TrainingType t";
            return entityManager.createQuery(query, TrainingType.class).getResultList();
        });
    }
}
