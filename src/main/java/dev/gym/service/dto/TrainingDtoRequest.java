package dev.gym.service.dto;

import java.time.LocalDate;

public record TrainingDtoRequest(Long traineeId, Long trainerId, String trainingName, TrainingTypeDto trainingType,
                                 LocalDate trainingDate, int trainingDuration) { }
