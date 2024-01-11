package dev.gym.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateTraineeDto(
        @NotEmpty(message = "Firstname must not be empty")
        String firstName,
        @NotEmpty(message = "Lastname must not be empty")
        String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dateOfBirth,
        String address,
        @NotNull(message = "Active status must not be empty")
        boolean isActive ) { }
