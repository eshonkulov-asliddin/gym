package dev.gym.service.impl;

import dev.gym.repository.TrainerRepository;
import dev.gym.repository.TrainingRepository;
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
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrainerServiceImpl extends AbstractUserService<TrainerDto, Long, Trainer> implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;
    private final ConversionService conversionService;

    @Autowired
    protected TrainerServiceImpl(TrainerRepository trainerRepository,
                                 TrainingRepository trainingRepository,
                                 ConversionService conversionService) {
        super(trainerRepository, conversionService);
        this.trainerRepository = trainerRepository;
        this.trainingRepository = trainingRepository;
        this.conversionService = conversionService;
    }

    public UserDto register(RegisterTrainerDto request) {
        Trainer trainer = conversionService.convert(request, Trainer.class);
        save(trainer);
        return conversionService.convert(trainer, UserDto.class);
    }

    public TrainerDto update(String username, UpdateTrainerDto request) {
        Trainer oldTrainer = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(ExceptionConstants.NOT_FOUND_MESSAGE, "Trainer", username)
                        )
                );
        Trainer newTrainer = conversionService.convert(request, Trainer.class);
        newTrainer.setUsername(username);
        oldTrainer.update(newTrainer);
        save(oldTrainer);
        return conversionService.convert(newTrainer, TrainerDto.class);
    }

    @Override
    public List<TrainerTrainingDto> getAllTrainingsByUsername(String trainerUsername, LocalDate from, LocalDate to, String traineeUsername) {
        if (!userRepository.existByUsername(trainerUsername)){
            throw new NotFoundException(
                    String.format(
                            ExceptionConstants.NOT_FOUND_MESSAGE, "Trainer", trainerUsername
                    )
            );
        }
        List<Training> allTrainingsByUsername = trainingRepository.findFor(traineeUsername, trainerUsername, from, to);
        return allTrainingsByUsername.stream()
                .map(training -> conversionService.convert(training, TrainerTrainingDto.class))
                .toList();
    }

    @Override
    public List<TrainerDto> getAllActiveUnAssignedTrainers() {
        List<Trainer> allActiveUnAssignedTrainers = trainerRepository.findActiveUnAssignedTrainers();
        return allActiveUnAssignedTrainers.stream()
                .map(trainer -> conversionService.convert(trainer, TrainerDto.class))
                .toList();
    }

    @Override
    protected Class<TrainerDto> getDtoClass() {
        return TrainerDto.class;
    }
}
