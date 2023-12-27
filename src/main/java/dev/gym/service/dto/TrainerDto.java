package dev.gym.service.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.gym.repository.model.TrainingType;

import java.util.List;

public record TrainerDto (
        String username,
        String firstName,
        String lastName,
        TrainingType specialization,
        boolean isActive,
        @JsonManagedReference
        List<TraineeDto> trainees ) { }
