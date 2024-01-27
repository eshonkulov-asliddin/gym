package dev.gym.service.impl;

import dev.gym.repository.TraineeRepository;
import dev.gym.repository.TrainerRepository;
import dev.gym.repository.TrainingRepository;
import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;
import dev.gym.security.credential.CredentialGenerator;
import dev.gym.security.jwt.JwtUtil;
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

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;
    private final CredentialGenerator credentialGenerator;
    private final ConversionService conversionService;
    private final JwtUtil jwtUtil;

    @Autowired
    public TraineeServiceImpl(TraineeRepository traineeRepository,
                              TrainerRepository trainerRepository,
                              TrainingRepository trainingRepository,
                              CredentialGenerator credentialGenerator,
                              ConversionService conversionService, JwtUtil jwtUtil) {
        super(traineeRepository, conversionService);
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingRepository = trainingRepository;
        this.credentialGenerator = credentialGenerator;
        this.conversionService = conversionService;
        this.jwtUtil = jwtUtil;
    }

    public UserDto register(RegisterTraineeDto request) {
        Trainee trainee = conversionService.convert(request, Trainee.class);
        trainee.setUsername(credentialGenerator.generateUsername(trainee.getFirstName(), trainee.getLastName()));
        trainee.setPassword(credentialGenerator.generatePassword());
        trainee.setActive(true);
        save(trainee);
        // generate token
        String token = jwtUtil.generateToken(trainee.getUsername());
        return new UserDto(trainee.getUsername(), token);
    }

    public TraineeDto update(String username, UpdateTraineeDto request) {
        Trainee oldTrainee = traineeRepository.findByUsername(username)
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
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(
                        String.format(
                                ExceptionConstants.NOT_FOUND_MESSAGE, "Trainee", username
                        )
                ));

        List<String> trainerUsernameList = trainerDtoList.stream()
                .map(TraineeTrainerDto::trainerUsername)
                .toList();

        List<Trainer> trainers = trainerRepository.findByUsernameIn(trainerUsernameList);

        trainee.addTrainers(trainers);
        save(trainee);
        return trainers.stream()
                .map(trainer -> conversionService.convert(trainer, TrainerDto.class))
                .toList();
    }

    @Override
    public List<TraineeTrainingDto> getAllTrainingsByUsername(String traineeUsername, LocalDate from, LocalDate to, String trainerUsername, String trainingTypeName) {
        if (!userRepository.existsByUsername(traineeUsername)) {
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
