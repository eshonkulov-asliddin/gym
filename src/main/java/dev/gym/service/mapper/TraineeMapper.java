package dev.gym.service.mapper;

import dev.gym.model.Trainee;
import dev.gym.service.dto.TraineeDtoRequest;
import dev.gym.service.dto.TraineeDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TraineeMapper extends UserMapper<TraineeDtoRequest, TraineeDtoResponse, Trainee> {

}