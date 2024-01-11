package dev.gym.service.converter.trainee;

import dev.gym.repository.model.Trainee;
import dev.gym.service.dto.UpdateTraineeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UpdateTraineeDtoToTraineeConverter implements Converter<UpdateTraineeDto, Trainee> {

    @Override
    public Trainee convert(UpdateTraineeDto source) {
        Trainee trainee = new Trainee();
        trainee.setFirstName(source.firstName());
        trainee.setLastName(source.lastName());
        trainee.setDateOfBirth(source.dateOfBirth());
        trainee.setAddress(source.address());
        trainee.setActive(source.isActive());
        return trainee;
    }
}
