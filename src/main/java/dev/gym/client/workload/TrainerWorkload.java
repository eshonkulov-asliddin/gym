package dev.gym.client.workload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TrainerWorkload (
        @NotEmpty(message = "Trainer username is required")
        String trainerUsername,
        @NotEmpty(message = "Trainer firstname is required")
        String trainerFirstname,
        @NotEmpty(message = "Trainer lastname is required")
        String trainerLastname,
        @NotNull(message = "Trainer workload is required")
        boolean isActive,
        @NotNull(message = "Training date is required")
        LocalDate trainingDate,
        @NotNull(message = "Training duration is required")
        int trainingDuration,
        @NotNull(message = "Action type is required")
        ActionType actionType) { }
