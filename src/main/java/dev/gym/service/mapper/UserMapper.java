package dev.gym.service.mapper;

import dev.gym.repository.datasource.credential.CredentialGenerator;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class UserMapper<T, R, M> implements BaseMapper<T, R, M> {

    @Autowired
    protected CredentialGenerator credentialsGenerator;

    @Mapping(target = "username", expression = "java(credentialsGenerator.generateUsername(dto.firstName(), dto.lastName()))")
    @Mapping(target = "password", expression = "java(credentialsGenerator.generatePassword())")
    public abstract M dtoToModel(T dto);

}
