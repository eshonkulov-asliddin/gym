package dev.gym.repository.impl;

import dev.gym.repository.TrainingRepository;
import dev.gym.repository.model.Training;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static dev.gym.repository.impl.util.TransactionUtil.executeInTransaction;

@Repository
public class TrainingRepositoryImpl extends AbstractCrudRepository<Training, Long> implements TrainingRepository {

    @Autowired
    public TrainingRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public List<Training> findFor(String traineeUsername, String trainerUsername, LocalDate from, LocalDate to) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            String query = "SELECT t FROM Training t " +
                    "JOIN FETCH t.trainee " +
                    "JOIN FETCH t.trainer " +
                    "WHERE (:traineeUsername IS NULL OR t.trainee.username = :traineeUsername) " +
                    "AND (:trainerUsername IS NULL OR t.trainer.username = :trainerUsername) " +
                    "AND (:from IS NULL OR t.trainingDate >= :from) " +
                    "AND (:to IS NULL OR t.trainingDate <= :to) ";

            return entityManager.createQuery(query, Training.class)
                    .setParameter("traineeUsername", traineeUsername)
                    .setParameter("trainerUsername", trainerUsername)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .getResultList();
        });
    }
}
