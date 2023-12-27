package dev.gym.service.converter.trainee;

import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Trainer;
import dev.gym.service.dto.TraineeDto;
import dev.gym.service.dto.TrainerDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class TraineeToTraineeDtoConverter implements Converter<Trainee, TraineeDto> {

    @Override
    public TraineeDto convert(Trainee source) {
        return new TraineeDto(  source.getUsername(),
                                source.getFirstName(),
                                source.getLastName(),
                                source.getDateOfBirth(),
                                source.getAddress(),
                                source.isActive(),
                                convertTrainers(source.getTrainers())
        );
    }

    private List<TrainerDto> convertTrainers(Set<Trainer> trainers) {
        return trainers.stream()
                .map(trainer -> new TrainerDto(
                        trainer.getUsername(),
                        trainer.getFirstName(),
                        trainer.getLastName(),
                        trainer.getSpecialization(),
                        trainer.isActive(),
                        null))
                .toList();
    }
}
