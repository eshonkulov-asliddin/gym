package dev.gym.repository.impl;

import dev.gym.repository.TraineeRepository;
import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Training;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static dev.gym.repository.impl.util.TransactionUtil.executeInTransaction;

@Repository
public class TraineeRepositoryImpl extends AbstractUserRepository<Trainee, Long> implements TraineeRepository {

    @Autowired
    public TraineeRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public List<Training> findAllTrainingsByUsername(String username, LocalDate from, LocalDate to, String trainerUsername, String trainingTypeName) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            LOGGER.info("Find all trainings for trainee with username {}", username);
            String query = "SELECT t FROM Training t " +
                        "JOIN FETCH t.trainee " +
                        "WHERE (t.trainee.username = :username) " +
                        "AND (:from IS NULL OR t.trainingDate >= :from) " +
                        "AND (:to IS NULL OR t.trainingDate <= :to) " +
                        "AND (:trainerName IS NULL OR t.trainer.username = :trainerName) " +
                        "AND (:trainingTypeName IS NULL OR t.trainingType.trainingTypeName = :trainingTypeName)";

            return entityManager.createQuery(query, Training.class)
                    .setParameter("username", username)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .setParameter("trainerName", trainerUsername)
                    .setParameter("trainingTypeName", trainingTypeName)
                    .getResultList();
        });
    }
}
