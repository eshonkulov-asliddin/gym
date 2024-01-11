package dev.gym.repository.impl;

import dev.gym.repository.UserRepository;
import dev.gym.repository.model.User;
import jakarta.persistence.EntityManagerFactory;

import java.util.Optional;

import static dev.gym.repository.impl.util.TransactionUtil.executeInTransaction;

@SuppressWarnings("unchecked")
public abstract class AbstractUserRepository<T extends User, K> extends AbstractCrudRepository<T, K> implements UserRepository<T, K> {

    AbstractUserRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public Optional<T> findByUsername(String username) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            LOGGER.info("Find {} by username {}", entityClass.getSimpleName(), username);
            String query = String.format(
                    "SELECT t FROM %s t WHERE t.username = :username",
                    entityClass.getSimpleName()
            );
            return Optional.of(entityManager.createQuery(query, entityClass)
                    .setParameter("username", username)
                    .getSingleResult());
        });
    }

    public void deleteByUsername(String username) {
        executeInTransaction(entityManagerFactory, entityManager -> {
            LOGGER.info("Delete {} by username {}", entityClass.getSimpleName(), username);
            String query = String.format(
                    "DELETE FROM %s t WHERE t.username = :username",
                    entityClass.getSimpleName()
            );
            entityManager.createQuery(query)
                    .setParameter("username", username)
                    .executeUpdate();
        });
    }

    public void updatePassword(String username, String newPassword) {
        executeInTransaction(entityManagerFactory, entityManager -> {
            LOGGER.info("Update password for {} with username {}", entityClass.getSimpleName(), username);
            String query = String.format(
                    "UPDATE %s t SET t.password = :password WHERE t.username = :username",
                    entityClass.getSimpleName()
            );
            entityManager.createQuery(query)
                    .setParameter("password", newPassword)
                    .setParameter("username", username)
                    .executeUpdate();
        });
    }

    public void setActiveStatus(String username, boolean activeStatus) {
        executeInTransaction(entityManagerFactory, entityManager -> {
            LOGGER.info("Update {} activeStatus with username {}", entityClass.getSimpleName(), username);
            String query = String.format(
                    "UPDATE %s t SET t.isActive = :activeStatus WHERE t.username = :username",
                    entityClass.getSimpleName()
            );
            entityManager.createQuery(query)
                    .setParameter("activeStatus", activeStatus)
                    .setParameter("username", username)
                    .executeUpdate();
        });
    }

    @Override
    public boolean existByUsername(String username) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            LOGGER.info("Check if {} with username {} exists", entityClass.getSimpleName(), username);
            String queryString = String.format(
                    "SELECT COUNT(t) FROM %s t WHERE t.username = :username",
                    entityClass.getSimpleName()
            );
            long count = entityManager.createQuery(queryString, Long.class)
                    .setParameter("username", username)
                    .getSingleResult();
            LOGGER.info("{} with username {} exists: {}", entityClass.getSimpleName(), username, count > 0);
            return count > 0;
        });
    }
}
