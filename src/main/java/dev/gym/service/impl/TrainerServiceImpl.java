package dev.gym.service.impl;

import dev.gym.repository.TrainerRepository;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;
import dev.gym.service.TrainerService;
import dev.gym.service.dto.RegisterTrainerDto;
import dev.gym.service.dto.TrainerDto;
import dev.gym.service.dto.TrainerTrainingDto;
import dev.gym.service.dto.UpdateTrainerDto;
import dev.gym.service.dto.UserDto;
import dev.gym.service.exception.NotFoundException;
import dev.gym.service.exception.util.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrainerServiceImpl extends AbstractUserService<TrainerDto, Long, Trainer> implements TrainerService {

    private final ConversionService conversionService;

    @Autowired
    protected TrainerServiceImpl(TrainerRepository trainerRepository,
                                 @Qualifier("customConvertionService") ConversionService conversionService) {
        super(trainerRepository, conversionService);
        this.conversionService = conversionService;
    }

    public UserDto register(RegisterTrainerDto request) {
        Trainer trainer = conversionService.convert(request, Trainer.class);
        save(trainer);
        return conversionService.convert(trainer, UserDto.class);
    }

    public TrainerDto update(String username, UpdateTrainerDto request) {
        Trainer byUsername = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(ExceptionConstants.NOT_FOUND_MESSAGE, "Trainer", username)
                        )
                );
        Trainer trainer = conversionService.convert(request, Trainer.class);
        byUsername.update(trainer);
        save(trainer);
        return conversionService.convert(trainer, TrainerDto.class);
    }

    @Override
    public Class<TrainerDto> getDtoClass() {
        return TrainerDto.class;
    }

    @Override
    public List<TrainerTrainingDto> getAllTrainingsByUsername(String username, LocalDate from, LocalDate to, String traineeUsername) {
        List<Training> allTrainingsByUsername = ((TrainerRepository) userRepository).findAllTrainingsByUsername(username, from, to, traineeUsername);
        return allTrainingsByUsername.stream()
                .map(training -> conversionService.convert(training, TrainerTrainingDto.class))
                .toList();
    }

    @Override
    public List<TrainerDto> getAllActiveUnAssignedTrainers() {
        List<Trainer> allActiveUnAssignedTrainers = ((TrainerRepository) userRepository).findActiveUnAssignedTrainers();
        return allActiveUnAssignedTrainers.stream()
                .map(trainer -> conversionService.convert(trainer, TrainerDto.class))
                .toList();
    }
}
