package dev.gym.service;

import dev.gym.repository.model.Trainer;
import dev.gym.service.dto.RegisterTrainerDto;
import dev.gym.service.dto.TrainerDto;
import dev.gym.service.dto.TrainerTrainingDto;
import dev.gym.service.dto.UpdateTrainerDto;
import dev.gym.service.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface TrainerService extends UserService<TrainerDto, Long, Trainer> {

    UserDto register(RegisterTrainerDto request);

    TrainerDto update(String username, UpdateTrainerDto request);

    List<TrainerTrainingDto> getAllTrainingsByUsername(String username, LocalDate from, LocalDate to, String traineeUsername);

    List<TrainerDto> getAllActiveUnAssignedTrainers();

}
