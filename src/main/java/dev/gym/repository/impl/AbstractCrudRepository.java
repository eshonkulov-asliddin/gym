package dev.gym.repository.impl;

import dev.gym.repository.CrudRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class AbstractCrudRepository<T, K> implements CrudRepository<T, K> {

    protected final Logger logger;

    protected final EntityManagerFactory entityManagerFactory;

    protected final Class<T> entityClass;

    AbstractCrudRepository(EntityManagerFactory entityManagerFactory) {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.logger = LoggerFactory.getLogger(entityClass);
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<T> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<T> query = entityManager.createQuery("select t from " + entityClass.getSimpleName() + " t", entityClass);
            return query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Error while finding all entities", e);
            return List.of();
        }
    }

    @Override
    public Optional<T> findById(K id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            logger.info("Find entity by id {}", id);
            return Optional.ofNullable(entityManager.find(entityClass, id));
        } catch (PersistenceException e) {
            logger.error("Error while finding entity by id {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public T save(T entity) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(entity);
            transaction.commit();
            logger.debug("Save or update entity {}", entity);
        } catch (PersistenceException e) {
            logger.error("Error while saving entity {}", entity, e);
        }
        return entity;
    }

    @Override
    public boolean delete(K id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            T reference = entityManager.getReference(entityClass, id);
            entityManager.remove(reference);
            transaction.commit();
            logger.info("Deleted entity by id {}", id);
            return true;
        } catch (EntityNotFoundException e) {
            logger.error("Not found entity by id {}", id, e);
            return false;
        } catch (PersistenceException e) {
            logger.error("Error while deleting entity by id {}", id, e);
            return false;
        }
    }

    @Override
    public Optional<T> findReferenceById(K id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            logger.info("Find entity by id {}", id);
            return Optional.ofNullable(entityManager.getReference(entityClass, id));
        } catch (PersistenceException e) {
            logger.error("Error while finding entity by id {}", id, e);
            return Optional.empty();
        }
    }
}