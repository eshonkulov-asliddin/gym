package dev.gym.repository;

import dev.gym.model.Trainee;
import dev.gym.repository.impl.AbstractCrudRepository;
import dev.gym.repository.impl.TraineeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TraineeRepositoryTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private AbstractCrudRepository<Trainee, Long> traineeRepository;
    private TypedQuery<Object> typedQuery;

    @BeforeEach
    void setUp() {
        entityManagerFactory = mock(EntityManagerFactory.class);
        entityManager = mock(EntityManager.class);
        entityTransaction = mock(EntityTransaction.class);
        typedQuery = mock(TypedQuery.class);

        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);

        traineeRepository = new TraineeRepository(entityManagerFactory);
    }

    @Test
    void testFindAll() {
        when(entityManager.createQuery(any(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(new Trainee()));

        assertEquals(traineeRepository.findAll().size(), 1);
    }

    @Test
    void testSave() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");

        doNothing().when(entityManager).persist(trainee);

        Trainee save = traineeRepository.save(trainee);

        assertEquals(save.getFirstName(), trainee.getFirstName());
    }

    @Test
    void testDelete() {
        Trainee trainee = new Trainee();

        when(entityManager.getReference(any(), any())).thenReturn(trainee);
        doNothing().when(entityManager).remove(trainee);

        assertTrue(traineeRepository.delete(1L));
    }
}
