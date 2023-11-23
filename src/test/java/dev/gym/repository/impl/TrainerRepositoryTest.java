package dev.gym.repository.impl;

import dev.gym.model.Specialization;
import dev.gym.model.Trainer;
import dev.gym.repository.config.RepositoryConfig;
import dev.gym.repository.datasource.credential.impl.SimpleCredentialGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = RepositoryConfig.class)
class TrainerRepositoryTest {

    private Trainer trainer;
    private final ApplicationContext context;
    private final SimpleCredentialGenerator simpleCredentialGenerator;

    @Autowired
    TrainerRepositoryTest(ApplicationContext context,
                          SimpleCredentialGenerator simpleCredentialGenerator) {
        this.context = context;
        this.simpleCredentialGenerator = simpleCredentialGenerator;
    }

    @BeforeEach
    void setUp(){
        trainer = new Trainer("John",
                "Doe",
                simpleCredentialGenerator,
                true,
                new Specialization("Fitness"));
    }

    @Test
    void testFindById() {
        TrainerRepository trainerRepository = context.getBean(TrainerRepository.class);
        // When
        trainerRepository.save(trainer);

        // Then
        assertTrue(trainerRepository.findById(trainer.getId()).isPresent());
    }
    @Test
    void testSaveTrainer() {
        TrainerRepository trainerRepository = context.getBean(TrainerRepository.class);

        // When
        trainerRepository.save(trainer);

        // Then
        assertTrue(trainerRepository.getData().containsKey(trainer.getId()));
    }

    @Test
    void testFindAll() {
        TrainerRepository trainerRepository = context.getBean(TrainerRepository.class);

        // When
        trainerRepository.save(trainer);

        // Then
        assertNotNull(trainerRepository.findAll());
    }

    @Test
    void testUpdateTrainer() {
        TrainerRepository trainerRepository = context.getBean(TrainerRepository.class);

        // When
        trainerRepository.save(trainer);
        String UPDATED_FIRST_NAME = "Jane";
        trainer.setFirstName(UPDATED_FIRST_NAME);
        trainerRepository.save(trainer);

        // Then
        assertTrue(trainerRepository.getData().containsKey(trainer.getId()));
        assertEquals(UPDATED_FIRST_NAME, trainerRepository.getData().get(trainer.getId()).getFirstName());
    }

    @Test
    void testDeleteTrainer() {
        TrainerRepository trainerRepository = context.getBean(TrainerRepository.class);

        // When
        trainerRepository.save(trainer);
        trainerRepository.deleteById(trainer.getId());

        // Then
        assertFalse(trainerRepository.getData().containsKey(trainer.getId()));
    }

}