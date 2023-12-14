package dev.gym.repository.impl;

import dev.gym.model.User;
import dev.gym.repository.UserRepository;
import jakarta.persistence.EntityManagerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import static dev.gym.repository.impl.util.TransactionUtil.executeInTransaction;

@SuppressWarnings("unchecked")
public abstract class AbstractUserRepository<T extends User, L, K> extends AbstractCrudRepository<T, K> implements UserRepository<T, L, K> {

    protected Class<L> trainingClass;

    AbstractUserRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.trainingClass = (Class<L>) genericSuperclass.getActualTypeArguments()[1];
    }

    public Optional<T> findByUsername(String username) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            logger.info("Find entity by username {}", username);
            String query = String.format("SELECT t FROM %s t WHERE t.username = :username", entityClass.getSimpleName());
            return Optional.of(entityManager.createQuery(query, entityClass)
                    .setParameter("username", username)
                    .getSingleResult());
        });
    }

    public void deleteByUsername(String username) {
        executeInTransaction(entityManagerFactory, entityManager -> {
            logger.info("Delete entity by username {}", username);
            String query = String.format("DELETE FROM %s t WHERE t.username = :username", entityClass.getSimpleName());
            entityManager.createQuery(query)
                    .setParameter("username", username)
                    .executeUpdate();
        });
    }

    public void updatePassword(K id, String newPassword) {
        executeInTransaction(entityManagerFactory, entityManager -> {
            logger.info("Update password for entity with id {}", id);
            String query = String.format("UPDATE %s t SET t.password = :password WHERE t.id = :id", entityClass.getSimpleName());
            entityManager.createQuery(query)
                    .setParameter("password", newPassword)
                    .setParameter("id", id)
                    .executeUpdate();
        });
    }

    public void setActiveStatus(K id, boolean status) {
        executeInTransaction(entityManagerFactory, entityManager -> {
            logger.info("Update active status for entity with id {}", id);
            String query = String.format("UPDATE %s t SET t.isActive = :status WHERE t.id = :id", entityClass.getSimpleName());
            entityManager.createQuery(query)
                    .setParameter("status", status)
                    .setParameter("id", id)
                    .executeUpdate();
        });
    }

    public List<L> findAllTrainingsByUsername(String username) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            logger.info("Find all trainings for user with username {}", username);
            String query = String.format("SELECT t FROM %s t JOIN FETCH t.%s WHERE t.%s.username = :username", trainingClass.getSimpleName(), entityClass.getSimpleName().toLowerCase(), entityClass.getSimpleName().toLowerCase());
            return entityManager.createQuery(query, trainingClass)
                    .setParameter("username", username)
                    .getResultList();
        });
    }
}
