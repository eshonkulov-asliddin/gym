package dev.gym.repository;

import dev.gym.repository.model.Trainee;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeRepository extends UserRepository<Trainee, Long> {

}
