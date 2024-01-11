package dev.gym.repository.impl;

import dev.gym.repository.TrainingTypeRepository;
import dev.gym.repository.model.TrainingType;
import dev.gym.repository.model.enums.TrainingTypeEnum;
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
    public TrainingType findByType(TrainingTypeEnum trainingTypeName) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            String query = "SELECT t FROM TrainingType t WHERE t.trainingTypeName = :trainingTypeName";
            return entityManager.createQuery(query, TrainingType.class)
                    .setParameter("trainingTypeName", trainingTypeName)
                    .getSingleResult();
        });
    }
}
