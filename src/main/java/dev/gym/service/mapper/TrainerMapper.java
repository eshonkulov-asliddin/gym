package dev.gym.service.mapper;

import dev.gym.model.Trainer;
import dev.gym.service.dto.TrainerDtoRequest;
import dev.gym.service.dto.TrainerDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TrainerMapper extends UserMapper<TrainerDtoRequest, TrainerDtoResponse, Trainer> {

}