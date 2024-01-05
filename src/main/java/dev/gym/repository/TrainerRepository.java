package dev.gym.repository;

import dev.gym.repository.model.Trainer;

import java.util.List;

public interface TrainerRepository extends UserRepository<Trainer, Long>{

    List<Trainer> findActiveUnAssignedTrainers();

    List<Trainer> findTrainersByUsernames(List<String> usernames);

}
