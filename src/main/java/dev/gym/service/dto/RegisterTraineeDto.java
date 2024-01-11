package dev.gym.service.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public record RegisterTraineeDto(
        @NotEmpty(message = "Firstname must not be empty")
        String firstName,
        @NotEmpty(message = "Lastname must not be empty")
        String lastName,
        LocalDate dateOfBirth,
        String address ) { }
