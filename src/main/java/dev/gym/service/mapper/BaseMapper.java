package dev.gym.service.mapper;

import java.util.List;

public interface BaseMapper<T, R, M> {

    List<R> modelListToDtoList(List<M> modelList);

    R modelToDto(M model);

    M dtoToModel(T dto);
}
