package dev.gym.repository.impl;

import dev.gym.repository.TrainingTypeRepository;
import dev.gym.repository.model.TrainingType;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static dev.gym.repository.impl.util.TransactionUtil.executeInTransaction;

@Repository
@RequiredArgsConstructor
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public List<TrainingType> findAll() {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            String query = "SELECT t FROM TrainingType t";
            return entityManager.createQuery(query, TrainingType.class).getResultList();
        });
    }

    @Override
    public TrainingType findByName(String name) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            String query = "SELECT t FROM TrainingType t WHERE t.trainingTypeName = :name";
            return entityManager.createQuery(query, TrainingType.class)
                    .setParameter("name", name)
                    .getSingleResult();
        });
    }
}
