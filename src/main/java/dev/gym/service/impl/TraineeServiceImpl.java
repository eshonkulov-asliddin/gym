package dev.gym.service.impl;

import dev.gym.repository.TraineeRepository;
import dev.gym.repository.TrainerRepository;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl extends AbstractUserService<TraineeDto, Long, Trainee> implements TraineeService {

    private final ConversionService conversionService;
    private final TrainerRepository trainerRepository;

    @Autowired
    public TraineeServiceImpl(TraineeRepository traineeRepository,
                              @Qualifier("customConvertionService") ConversionService conversionService,
                              TrainerRepository trainerRepository) {
        super(traineeRepository, conversionService);
        this.conversionService = conversionService;
        this.trainerRepository = trainerRepository;
    }

    public UserDto register(RegisterTraineeDto request) {
        Trainee trainee = conversionService.convert(request, Trainee.class);
        save(trainee);
        return conversionService.convert(trainee, UserDto.class);
    }

    public TraineeDto update(String username, UpdateTraineeDto request) {
        Trainee byUsername = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(ExceptionConstants.NOT_FOUND_MESSAGE, "Trainee", username)
                        )
                );

        Trainee trainee = conversionService.convert(request, Trainee.class);
        trainee.setUsername(username);
        byUsername.update(trainee);
        save(byUsername);
        return conversionService.convert(trainee, TraineeDto.class);
    }

    @Override
    public List<TrainerDto> updateTrainers(String username, List<TraineeTrainerDto> trainerDtoList) {
        List<Trainer> trainers = trainerDtoList.stream()
                .map(trainerDto -> trainerRepository.findByUsername(trainerDto.trainerUsername()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        Trainee byUsername = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(
                        String.format(
                                ExceptionConstants.NOT_FOUND_MESSAGE, "Trainee", username
                        )
                ));

        byUsername.addTrainers(trainers);
        save(byUsername);
        return trainers.stream()
                .map(trainer -> conversionService.convert(trainer, TrainerDto.class))
                .toList();
    }

    @Override
    public List<TraineeTrainingDto> getAllTrainingsByUsername(String username, LocalDate from, LocalDate to, String trainerUsername, String trainingTypeName) {
        if (!userRepository.existByUsername(username)) {
            throw new NotFoundException(
                    String.format(
                            ExceptionConstants.NOT_FOUND_MESSAGE, "Trainee", username
                    )
            );
        }
        List<Training> allTrainingsByUsername = ((TraineeRepository) userRepository).findAllTrainingsByUsername(username, from, to, trainerUsername, trainingTypeName);
        return allTrainingsByUsername.stream()
                .map(training -> conversionService.convert(training, TraineeTrainingDto.class))
                .toList();
    }

    @Override
    public Class<TraineeDto> getDtoClass() {
        return TraineeDto.class;
    }
}
