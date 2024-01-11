package dev.gym.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record TraineeDto (
        String username,
        String firstName,
        String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dateOfBirth,
        String address,
        boolean isActive,
        List<TrainerDto> trainers ) { }
