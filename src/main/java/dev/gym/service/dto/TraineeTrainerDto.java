package dev.gym.service.dto;

import jakarta.validation.constraints.NotEmpty;

public record TraineeTrainerDto (
        @NotEmpty(message = "trainerUsername must not be empty")
        String trainerUsername) {}
