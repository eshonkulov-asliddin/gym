package dev.gym.repository;

import dev.gym.repository.model.TrainingType;
import dev.gym.repository.model.enums.TrainingTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long> {

    @Query("SELECT t FROM TrainingType t WHERE t.trainingTypeName = :trainingTypeName")
    TrainingType findByTrainingTypeName(@Param("trainingTypeName") TrainingTypeEnum trainingTypeName);
}
