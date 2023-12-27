package dev.gym.service.dto;

import dev.gym.repository.model.TrainingType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateTrainerDto(
        @NotEmpty(message = "Firstname must not be empty")
        String firstName,
        @NotEmpty(message = "Lastname must not be empty")
        String lastName,
        @NotNull(message = "Specialization must not be empty")
        TrainingType specialization,
        @NotNull(message = "Active status must not be empty")
        boolean isActive ) { }
