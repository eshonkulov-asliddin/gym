package dev.gym.service.converter.trainer;

import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Trainer;
import dev.gym.service.dto.TraineeDto;
import dev.gym.service.dto.TrainerDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class TrainerToTrainerDtoConverter implements Converter<Trainer, TrainerDto> {

    @Override
    public TrainerDto convert(Trainer source) {
        return new TrainerDto(  source.getUsername(),
                                source.getFirstName(),
                                source.getLastName(),
                                source.getSpecialization(),
                                source.isActive(),
                                convertTrainees(source.getTrainees())
                );
    }

    private List<TraineeDto> convertTrainees(Set<Trainee> trainees) {
        return trainees.stream()
                .map(trainee -> new TraineeDto(
                                trainee.getUsername(),
                                trainee.getFirstName(),
                                trainee.getLastName(),
                                trainee.getDateOfBirth(),
                                trainee.getAddress(),
                                trainee.isActive(),
                                null))
                .toList();
    }
}
