package dev.gym.repository.impl;

import dev.gym.model.BaseEntity;
import dev.gym.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
abstract class AbstractRepository<T extends BaseEntity<K>, K>
        implements BaseRepository<T, K>{
    private final Logger logger;
    @Autowired
    EntityManagerFactory entityManagerFactory;

    private final Class<T> entityClass;
    AbstractRepository(){
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.logger = LoggerFactory.getLogger(entityClass);
    }

    @Override
    public List<T> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        TypedQuery<T> query = entityManager.createQuery("select t from "
            + entityClass.getSimpleName() + " t", entityClass);
        return query.getResultList();
    }

    @Override
    public Optional<T> findById(K id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        logger.info("Find entity by id {}", id);
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public T save(T entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        logger.info("Saving entity {}", entity);
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
        return entity;
    }

    @Override
    public boolean deleteById(K id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        logger.info("Deleting entity by id {}", id);
        T reference = entityManager.getReference(entityClass, id);
        entityManager.remove(reference);

        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    public Optional<T> findProxyById(K id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        logger.info("Find entity by id {}", id);
        return Optional.ofNullable(entityManager.getReference(entityClass, id));
    }
}
