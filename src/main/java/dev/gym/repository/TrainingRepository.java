package dev.gym.repository;

import dev.gym.repository.model.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainingRepository extends CrudRepository<Training, Long> {

    List<Training> findFor(String traineeUsername, String trainerUsername, LocalDate from, LocalDate to);
}
