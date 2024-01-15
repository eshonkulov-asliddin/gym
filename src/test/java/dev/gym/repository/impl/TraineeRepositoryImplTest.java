//package dev.gym.repository.impl;
//
//import dev.gym.repository.TraineeRepository;
//import dev.gym.repository.model.Trainee;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.EntityTransaction;
//import jakarta.persistence.TypedQuery;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class TraineeRepositoryImplTest {
//
//    @Mock
//    private EntityManagerFactory entityManagerFactory;
//    @Mock
//    private EntityManager entityManager;
//    @Mock
//    private EntityTransaction entityTransaction;
//    @Mock
//    private TypedQuery<Object> typedQuery;
//    @InjectMocks
//    private TraineeRepository traineeRepository;
//
//    @BeforeEach
//    void setUp() {
//        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
//        when(entityManager.getTransaction()).thenReturn(entityTransaction);
//    }
//
//    @Test
//    void testFindAll() {
//        when(entityManager.createQuery(any(), any())).thenReturn(typedQuery);
//        when(typedQuery.getResultList()).thenReturn(List.of(new Trainee()));
//
//        assertEquals(traineeRepository.findAll().size(), 1);
//    }
//
//    @Test
//    void testSave() {
//        Trainee trainee = new Trainee();
//        trainee.setFirstName("John");
//        trainee.setLastName("Doe");
//
//        doNothing().when(entityManager).persist(trainee);
//
//        traineeRepository.save(trainee);
//    }
//
////    @Test
////    void testDelete() {
////        Trainee trainee = new Trainee();
////
////        when(entityManager.getReference(any(), any())).thenReturn(trainee);
////        doNothing().when(entityManager).remove(trainee);
////
////        assertTrue(traineeRepository.delete(1L));
////    }
//}
