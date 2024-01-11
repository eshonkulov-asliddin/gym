package dev.gym.service.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateTrainingDto(
        @NotEmpty(message = "Trainee trainerUsername must not be empty")
        String traineeUsername,
        @NotEmpty(message = "Trainer trainerUsername must not be empty")
        String trainerUsername,
        @NotEmpty(message = "Training name must not be empty")
        String trainingName,
        @Future(message = "Training date must not be empty")
        LocalDate trainingDate,
        @NotNull(message = "Training duration must not be empty")
        int trainingDuration ) { }
