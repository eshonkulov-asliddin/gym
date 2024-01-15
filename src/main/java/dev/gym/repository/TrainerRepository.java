package dev.gym.repository;

import dev.gym.repository.model.Trainer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainerRepository extends UserRepository<Trainer, Long> {

    @Query("SELECT t FROM Trainer t WHERE t.isActive = true AND t.trainees IS EMPTY")
    List<Trainer> findByIsActiveTrueAndAssignedUsersIsEmpty();

    @Query("SELECT t FROM Trainer t WHERE t.username IN :usernames")
    List<Trainer> findByUsernameIn(@Param("usernames") List<String> usernames);
}
