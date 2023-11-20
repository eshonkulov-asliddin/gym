package dev.gym.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainingTest {

    @Test
    void testGetterSetter() {
        // When
        Trainee trainee  = mock(Trainee.class);
        Trainer trainer = mock(Trainer.class);

        // Then
        when(trainee.getId()).thenReturn(1L);
        when(trainer.getId()).thenReturn(1L);

        TrainingType trainingType = new TrainingType("Fitness");
        String trainingName = "Training Name";
        LocalDate trainingDate = LocalDate.of(2021, 1, 1);
        int trainingDuration = 1;

        // Creating a Training
        Training training = new Training();

        // Setting values
        training.setId(1L);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName(trainingName);
        training.setTrainingType(trainingType);
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(trainingDuration);

        // Asserting values
        assertEquals(1L, training.getId());
        assertEquals(trainee, training.getTrainee());
        assertEquals(trainer, training.getTrainer());
        assertEquals(trainingName, training.getTrainingName());
        assertEquals(trainingType, training.getTrainingType());
        assertEquals(trainingDate, training.getTrainingDate());
        assertEquals(trainingDuration, training.getTrainingDuration());
    }

    @Test
    void testHashCodeEquals(){
        // When
        Trainee trainee  = mock(Trainee.class);
        Trainer trainer = mock(Trainer.class);

        // Then
        when(trainee.getId()).thenReturn(1L);
        when(trainer.getId()).thenReturn(1L);

        TrainingType trainingType = new TrainingType("Fitness");
        String trainingName = "Training Name";
        LocalDate trainingDate = LocalDate.of(2021, 1, 1);
        int trainingDuration = 1;

        // Creating a Training
        Training training1 = new Training();
        Training training2 = new Training();

        // Setting values
        training1.setTrainee(trainee);
        training1.setTrainer(trainer);
        training1.setTrainingName(trainingName);
        training1.setTrainingType(trainingType);
        training1.setTrainingDate(trainingDate);
        training1.setTrainingDuration(trainingDuration);

        training2.setTrainee(trainee);
        training2.setTrainer(trainer);
        training2.setTrainingName(trainingName);
        training2.setTrainingType(trainingType);
        training2.setTrainingDate(trainingDate);
        training2.setTrainingDuration(trainingDuration);

        // Asserting values
        assertTrue(training1.equals(training2));
        assertEquals(training1.hashCode(), training2.hashCode());
    }

    @Test
    void testToString(){
        // When
        Trainee trainee  = mock(Trainee.class);
        Trainer trainer = mock(Trainer.class);

        // Then
        when(trainee.getId()).thenReturn(1L);
        when(trainer.getId()).thenReturn(1L);

        TrainingType trainingType = new TrainingType("Fitness");
        String trainingName = "Training Name";
        LocalDate trainingDate = LocalDate.of(2021, 1, 1);
        int trainingDuration = 1;

        // Creating a Training
        Training training = new Training();

        // Setting values
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName(trainingName);
        training.setTrainingType(trainingType);
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(trainingDuration);

        // Asserting values
        assertEquals(String.format("Training(id=%s, trainee=%s, trainer=%s, trainingName=%s, trainingType=%s, trainingDate=%s, trainingDuration=%s)",
                training.getId(),
                trainee,
                trainer,
                trainingName,
                trainingType,
                trainingDate,
                trainingDuration
        ), training.toString(), "toString method is not correct");
    }
}
