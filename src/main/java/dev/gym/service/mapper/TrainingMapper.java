package dev.gym.service.mapper;

import dev.gym.model.Trainee;
import dev.gym.model.Trainer;
import dev.gym.model.Training;
import dev.gym.repository.UserRepository;
import dev.gym.service.dto.TrainingDtoReponse;
import dev.gym.service.dto.TrainingDtoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TrainingMapper implements BaseMapper<TrainingDtoRequest, TrainingDtoReponse, Training> {

    @Autowired
    protected UserRepository<Trainee, Training, Long> traineeRepository;

    @Autowired
    protected UserRepository<Trainer, Training, Long> trainerRepository;

    public abstract List<TrainingDtoReponse> modelListToDtoList(List<Training> modelList);

    @Mapping(target = "traineeId", expression = "java(model.getTrainee().getId())")
    @Mapping(target = "trainerId", expression = "java(model.getTrainer().getId())")
    public abstract TrainingDtoReponse modelToDto(Training model);

    @Mapping(target = "trainee", expression = "java(traineeRepository.findById(dto.traineeId()).orElse(null))")
    @Mapping(target = "trainer", expression = "java(trainerRepository.findById(dto.trainerId()).orElse(null))")
    public abstract Training dtoToModel(TrainingDtoRequest dto);

}