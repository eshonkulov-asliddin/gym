package dev.gym.repository.impl;

import dev.gym.repository.TrainerRepository;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static dev.gym.repository.impl.util.TransactionUtil.executeInTransaction;

@Repository
public class TrainerRepositoryImpl extends AbstractUserRepository<Trainer, Long> implements TrainerRepository {

    TrainerRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public List<Trainer> findActiveUnAssignedTrainers() {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            String query = "SELECT t FROM Trainer t WHERE t.isActive = true AND t.trainees IS EMPTY ";
            return entityManager.createQuery(query, Trainer.class).getResultList();
        });
    }

    public List<Training> findAllTrainingsByUsername(String username, LocalDate from, LocalDate to, String traineeUsername) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            String query = "SELECT t FROM Training t " +
                    "JOIN FETCH t.trainer " +
                    "WHERE (t.trainer.username = :username) " +
                    "AND (:from IS NULL OR t.trainingDate >= :from) " +
                    "AND (:to IS NULL OR t.trainingDate <= :to) " +
                    "AND (:traineeName IS NULL OR t.trainee.username = :traineeName)";

            return entityManager.createQuery(query, Training.class)
                    .setParameter("username", username)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .setParameter("traineeName", traineeUsername)
                    .getResultList();
        });
    }
}
