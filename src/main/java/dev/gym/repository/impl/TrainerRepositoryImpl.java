package dev.gym.repository.impl;

import dev.gym.repository.TrainerRepository;
import dev.gym.repository.model.Trainer;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;

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

    @Override
    public List<Trainer> findTrainersByUsernames(List<String> usernames) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            String query = "SELECT t FROM Trainer t WHERE t.username IN :usernames";
            return entityManager.createQuery(query, Trainer.class)
                    .setParameter("usernames", usernames)
                    .getResultList();
        });
    }
}
