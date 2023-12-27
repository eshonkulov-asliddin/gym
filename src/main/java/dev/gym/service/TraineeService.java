package dev.gym.service;

import dev.gym.repository.model.Trainee;
import dev.gym.service.dto.RegisterTraineeDto;
import dev.gym.service.dto.TraineeDto;
import dev.gym.service.dto.TraineeTrainerDto;
import dev.gym.service.dto.TraineeTrainingDto;
import dev.gym.service.dto.TrainerDto;
import dev.gym.service.dto.UpdateTraineeDto;
import dev.gym.service.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface TraineeService extends UserService<TraineeDto, Long, Trainee> {

    UserDto register(RegisterTraineeDto request);

    TraineeDto update(String username, UpdateTraineeDto request);

    List<TrainerDto> updateTrainers(String username, List<TraineeTrainerDto> trainerDtoList);

    List<TraineeTrainingDto> getAllTrainingsByUsername(String username, LocalDate from, LocalDate to, String trainerUsername, String trainingTypeName);

}
