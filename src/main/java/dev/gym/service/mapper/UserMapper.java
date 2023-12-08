package dev.gym.service.mapper;

import dev.gym.repository.datasource.credential.impl.SimpleCredentialGenerator;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class UserMapper<T, R, M> {

    @Autowired
    protected SimpleCredentialGenerator credentialsGenerator;

    public abstract List<R> modelListToDtoList(List<M> modelList);

    public abstract R modelToDto(M model);

    @Mapping(target = "username", expression = "java(credentialsGenerator.generateUsername(dto.firstName(), dto.lastName()))")
    @Mapping(target = "password", expression = "java(credentialsGenerator.generatePassword())")
    public abstract M dtoToModel(T dto);

}