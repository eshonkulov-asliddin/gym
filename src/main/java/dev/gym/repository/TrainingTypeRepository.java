package dev.gym.repository;

import dev.gym.repository.model.TrainingType;

import java.util.List;

public interface TrainingTypeRepository {

    List<TrainingType> findAll();

}
