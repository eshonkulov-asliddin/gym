package dev.gym.service.impl;

import dev.gym.repository.TraineeRepository;
import dev.gym.repository.TrainerRepository;
import dev.gym.repository.TrainingRepository;
import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;
import dev.gym.service.TraineeService;
import dev.gym.service.dto.RegisterTraineeDto;
import dev.gym.service.dto.TraineeDto;
import dev.gym.service.dto.TraineeTrainerDto;
import dev.gym.service.dto.TraineeTrainingDto;
import dev.gym.service.dto.TrainerDto;
import dev.gym.service.dto.UpdateTraineeDto;
import dev.gym.service.dto.UserDto;
import dev.gym.service.exception.NotFoundException;
import dev.gym.service.exception.util.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TraineeServiceImpl extends AbstractUserService<TraineeDto, Long, Trainee> implements TraineeService {

    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;
    private final ConversionService conversionService;

    @Autowired
    public TraineeServiceImpl(TraineeRepository traineeRepository,
                              TrainerRepository trainerRepository,
                              TrainingRepository trainingRepository,
                              ConversionService conversionService) {
        super(traineeRepository, conversionService);
        this.trainerRepository = trainerRepository;
        this.trainingRepository = trainingRepository;
        this.conversionService = conversionService;
    }

    public UserDto register(RegisterTraineeDto request) {
        Trainee trainee = conversionService.convert(request, Trainee.class);
        save(trainee);
        return conversionService.convert(trainee, UserDto.class);
    }

    public TraineeDto update(String username, UpdateTraineeDto request) {
        Trainee oldTrainee = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(ExceptionConstants.NOT_FOUND_MESSAGE, "Trainee", username)
                        )
                );

        Trainee newTrainee = conversionService.convert(request, Trainee.class);
        newTrainee.setUsername(username);
        oldTrainee.update(newTrainee);
        save(oldTrainee);
        return conversionService.convert(newTrainee, TraineeDto.class);
    }

    @Override
    public List<TrainerDto> updateTrainers(String username, List<TraineeTrainerDto> trainerDtoList) {
        Trainee trainee = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(
                        String.format(
                                ExceptionConstants.NOT_FOUND_MESSAGE, "Trainee", username
                        )
                ));

        List<String> trainerUsernameList = trainerDtoList.stream()
                .map(TraineeTrainerDto::trainerUsername)
                .toList();

        List<Trainer> trainers = trainerRepository.findTrainersByUsernames(trainerUsernameList);

        trainee.addTrainers(trainers);
        save(trainee);
        return trainers.stream()
                .map(trainer -> conversionService.convert(trainer, TrainerDto.class))
                .toList();
    }

    @Override
    public List<TraineeTrainingDto> getAllTrainingsByUsername(String traineeUsername, LocalDate from, LocalDate to, String trainerUsername, String trainingTypeName) {
        if (!userRepository.existByUsername(traineeUsername)) {
            throw new NotFoundException(
                    String.format(
                            ExceptionConstants.NOT_FOUND_MESSAGE, "Trainee", traineeUsername
                    )
            );
        }
        List<Training> allTrainingsByUsername = trainingRepository.findFor(traineeUsername, trainerUsername, from, to);
        return allTrainingsByUsername.stream()
                .map(training -> conversionService.convert(training, TraineeTrainingDto.class))
                .toList();
    }

    @Override
    protected Class<TraineeDto> getDtoClass() {
        return TraineeDto.class;
    }
}
