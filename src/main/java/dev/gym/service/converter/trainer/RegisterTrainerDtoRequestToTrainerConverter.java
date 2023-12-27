package dev.gym.service.converter.trainer;

import dev.gym.repository.datasource.credential.CredentialGenerator;
import dev.gym.repository.model.Trainer;
import dev.gym.service.dto.RegisterTrainerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterTrainerDtoRequestToTrainerConverter implements Converter<RegisterTrainerDto, Trainer> {

    private final CredentialGenerator credentialGenerator;

    @Override
    public Trainer convert(RegisterTrainerDto source) {
        Trainer trainer = new Trainer();
        trainer.setFirstName(source.firstName());
        trainer.setLastName(source.lastName());
        trainer.setUsername(credentialGenerator.generateUsername(trainer.getFirstName(), trainer.getLastName()));
        trainer.setPassword(credentialGenerator.generatePassword());
        trainer.setSpecialization(source.specialization());
        return trainer;
    }
}
