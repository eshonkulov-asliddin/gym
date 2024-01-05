package dev.gym.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.gym.repository.model.enums.TrainingTypeEnum;

import java.time.LocalDate;

public record TrainerTrainingDto (
        String trainingName,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate trainingDate,
        TrainingTypeEnum trainingType,
        int trainingDuration,
        String traineeUsername) { }
