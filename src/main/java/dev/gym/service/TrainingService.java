package dev.gym.service;

import dev.gym.repository.model.Training;
import dev.gym.service.dto.AddTrainingDto;

public interface TrainingService extends CrudService<AddTrainingDto, Long, Training> {

    void addTraining(AddTrainingDto request);
}
