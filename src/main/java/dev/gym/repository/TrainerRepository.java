package dev.gym.repository;

import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainerRepository extends UserRepository<Trainer, Long>{

    List<Trainer> findActiveUnAssignedTrainers();

    List<Training> findAllTrainingsByUsername(String username, LocalDate from, LocalDate to, String traineeUsername);

}
