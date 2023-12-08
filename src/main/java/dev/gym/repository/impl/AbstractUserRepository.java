package dev.gym.repository.impl;

import dev.gym.model.User;
import dev.gym.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class AbstractUserRepository<T extends User, L, K> extends AbstractCrudRepository<T, K> implements UserRepository<T, L, K> {

    protected Class<L> trainingClass;

    AbstractUserRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.trainingClass = (Class<L>) genericSuperclass.getActualTypeArguments()[1];
    }

    public Optional<T> findByUsername(String username) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            String query = String.format("SELECT t FROM %s t WHERE t.username = :username", entityClass.getSimpleName());
            return Optional.of(entityManager.createQuery(query, entityClass)
                    .setParameter("username", username)
                    .getSingleResult());
        } catch (NoResultException e) {
            logger.error(String.format("Error while finding entity by username %s", username), e);
            return Optional.empty();
        }
    }

    public void deleteByUsername(String username) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            String query = String.format("DELETE FROM %s t WHERE t.username = :username", entityClass.getSimpleName());
            entityManager.createQuery(query)
                    .setParameter("username", username)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } catch (NoResultException e) {
            logger.error(String.format("Error while deleting entity by username %s", username), e);
        }
    }

    public void updatePassword(K id, String newPassword) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            String query = String.format("UPDATE %s t SET t.password = :password WHERE t.id = :id", entityClass.getSimpleName());
            entityManager.createQuery(query).setParameter("password", newPassword).setParameter("id", id).executeUpdate();
            entityManager.getTransaction().commit();
        } catch (NoResultException e) {
            logger.error(String.format("Error while updating password for entity with id %s", id), e);
        }
    }

    public void setActiveStatus(K id, boolean status) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            String query = String.format("UPDATE %s t SET t.isActive = :status WHERE t.id = :id", entityClass.getSimpleName());
            entityManager.createQuery(query)
                    .setParameter("status", status)
                    .setParameter("id", id)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } catch (NoResultException e) {
            logger.error(String.format("Error while updating active status for entity with id %s", id), e);

        }
    }

    public List<L> findAllTrainingsByUsername(String username) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            String entityAlias = "t";
            String entityName = entityClass.getSimpleName();
            String jpql = String.format("SELECT %s FROM Training %s JOIN FETCH %s.%s WHERE %s.%s.username = :username",
                    entityAlias, entityAlias, entityAlias, entityName.toLowerCase(), entityAlias, entityName.toLowerCase());

            return entityManager.createQuery(jpql, trainingClass)
                    .setParameter("username", username)
                    .getResultList();
        } catch (NoResultException e) {
            logger.error(String.format("Error while finding trainings for user with username %s", username), e);
            return List.of();
        }
    }
}