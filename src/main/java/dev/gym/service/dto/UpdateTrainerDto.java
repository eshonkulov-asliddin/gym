package dev.gym.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateTrainerDto(
        @NotEmpty(message = "Firstname must not be empty")
        String firstName,
        @NotEmpty(message = "Lastname must not be empty")
        String lastName,
        @NotEmpty(message = "Specialization must not be empty")
        String specialization,
        @NotNull(message = "Active status must not be empty")
        boolean isActive ) { }
