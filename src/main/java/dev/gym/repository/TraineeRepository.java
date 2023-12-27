package dev.gym.repository;

import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Training;

import java.time.LocalDate;
import java.util.List;

public interface TraineeRepository extends UserRepository<Trainee, Long> {

    List<Training> findAllTrainingsByUsername(String username, LocalDate from, LocalDate to, String trainerUsername, String trainingTypeName);

}
