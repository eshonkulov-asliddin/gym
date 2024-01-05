package dev.gym.service;

import dev.gym.repository.model.Training;
import dev.gym.service.dto.CreateTrainingDto;

public interface TrainingService extends CrudService<CreateTrainingDto, Long, Training> {

    void addTraining(CreateTrainingDto request);
}
