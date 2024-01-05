package dev.gym.service.converter.trainee;

import dev.gym.repository.datasource.credential.CredentialGenerator;
import dev.gym.repository.model.Trainee;
import dev.gym.service.dto.RegisterTraineeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterTraineeDtoToTraineeConverter implements Converter<RegisterTraineeDto, Trainee> {

    private final CredentialGenerator credentialGenerator;

    @Override
    public Trainee convert(RegisterTraineeDto source) {
        Trainee trainee = new Trainee();
        trainee.setFirstName(source.firstName());
        trainee.setLastName(source.lastName());
        trainee.setUsername(credentialGenerator.generateUsername(trainee.getFirstName(), trainee.getLastName()));
        trainee.setPassword(credentialGenerator.generatePassword());
        trainee.setDateOfBirth(source.dateOfBirth());
        trainee.setAddress(source.address());
        trainee.setActive(true);
        return trainee;
    }
}
