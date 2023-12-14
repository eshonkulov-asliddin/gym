package dev.gym.repository.impl;

import dev.gym.repository.CrudRepository;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import static dev.gym.repository.impl.util.TransactionUtil.executeInTransaction;

@SuppressWarnings("unchecked")
public abstract class AbstractCrudRepository<T, K> implements CrudRepository<T, K> {

    protected final Logger logger;

    protected final EntityManagerFactory entityManagerFactory;

    protected final Class<T> entityClass;

    protected final Class<K> entityIdClass;


    protected AbstractCrudRepository(EntityManagerFactory entityManagerFactory) {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.entityIdClass = (Class<K>) genericSuperclass.getActualTypeArguments()[1];
        this.logger = LoggerFactory.getLogger(entityClass);
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<T> findAll() {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            logger.info("Find all entities");
            String query = "select t from " + entityClass.getSimpleName() + " t";
            return entityManager.createQuery(query, entityClass).getResultList();
        });
    }

    @Override
    public Optional<T> findById(K id) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            logger.info("Find entity by id {}", id);
            return Optional.ofNullable(entityManager.find(entityClass, id));
        });
    }

    @Override
    public T save(T entity) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            logger.info("Save entity {}", entity);
            entityManager.merge(entity);
            return entity;
        });
    }

    @Override
    public boolean delete(K id) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            logger.info("Delete entity by id {}", id);
            T reference = entityManager.getReference(entityClass, id);
            entityManager.remove(reference);
            return true;
        });
    }

    @Override
    public boolean existById(K id) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            logger.info("Check if entity with id {} exists", id);
            String query = "select count(t) from " + entityClass.getSimpleName() + " t where t.id = :id";
            return entityManager.createQuery(query, Long.class)
                    .setParameter("id", id)
                    .getSingleResult() > 0;
        });
    }
}
