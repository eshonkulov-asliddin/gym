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

    protected final Class<T> entityClass;
    protected final Class<K> entityIdClass;
    protected final Logger LOGGER;
    protected final EntityManagerFactory entityManagerFactory;

    protected AbstractCrudRepository(EntityManagerFactory entityManagerFactory) {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.entityIdClass = (Class<K>) genericSuperclass.getActualTypeArguments()[1];
        this.entityManagerFactory = entityManagerFactory;
        this.LOGGER = LoggerFactory.getLogger(entityClass);
    }

    @Override
    public List<T> findAll() {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            LOGGER.info("Find all {}", entityClass.getSimpleName());
            String query = "SELECT t FROM " + entityClass.getSimpleName() + " t";
            return entityManager.createQuery(query, entityClass).getResultList();
        });
    }

    @Override
    public Optional<T> findById(K id) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            LOGGER.info("Find {} by id {}", entityClass.getSimpleName(), id);
            return Optional.ofNullable(entityManager.find(entityClass, id));
        });
    }

    @Override
    public void save(T entity) {
        executeInTransaction(entityManagerFactory, entityManager -> {
            LOGGER.info("Save {}", entity);
            entityManager.merge(entity);
        });
    }

    @Override
    public boolean delete(K id) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            LOGGER.info("Delete {} by id {}", entityClass.getSimpleName(), id);
            T reference = entityManager.getReference(entityClass, id);
            entityManager.remove(reference);
            return true;
        });
    }

    @Override
    public boolean existById(K id) {
        return executeInTransaction(entityManagerFactory, entityManager -> {
            LOGGER.info("Check if {} with id {} exists", entityClass.getSimpleName(), id);
            String query = "SELECT COUNT(t) FROM " + entityClass.getSimpleName() + " t WHERE t.id = :id";
            return entityManager.createQuery(query, Long.class)
                    .setParameter("id", id)
                    .getSingleResult() > 0;
        });
    }
}
