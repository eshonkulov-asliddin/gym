package dev.gym.repository;

import dev.gym.repository.model.Training;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Query("SELECT t FROM Training t " +
            "JOIN FETCH t.trainee " +
            "JOIN FETCH t.trainer " +
            "WHERE (:traineeUsername IS NULL OR t.trainee.username = :traineeUsername) " +
            "AND (:trainerUsername IS NULL OR t.trainer.username = :trainerUsername) " +
            "AND (:from IS NULL OR t.trainingDate >= :from) " +
            "AND (:to IS NULL OR t.trainingDate <= :to)")
    List<Training> findFor(
            @Nullable @Param("traineeUsername") String traineeUsername,
            @Nullable @Param("trainerUsername") String trainerUsername,
            @Nullable @Param("from") LocalDate from,
            @Nullable @Param("to") LocalDate to
    );
}
