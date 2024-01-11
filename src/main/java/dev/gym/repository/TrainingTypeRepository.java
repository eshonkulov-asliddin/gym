package dev.gym.repository;

import dev.gym.repository.model.TrainingType;
import dev.gym.repository.model.enums.TrainingTypeEnum;

import java.util.List;

public interface TrainingTypeRepository {

    List<TrainingType> findAll();

    TrainingType findByType(TrainingTypeEnum trainingTypeEnum);

}
