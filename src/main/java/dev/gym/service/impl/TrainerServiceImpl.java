package dev.gym.service.impl;

import dev.gym.repository.TrainerRepository;
import dev.gym.repository.TrainingRepository;
import dev.gym.repository.TrainingTypeRepository;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;
import dev.gym.repository.model.enums.TrainingTypeEnum;
import dev.gym.security.credential.CredentialGenerator;
import dev.gym.security.jwt.JwtUtil;
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
    private final TrainingTypeRepository trainingTypeRepository;
    private final CredentialGenerator credentialGenerator;
    private final ConversionService conversionService;
    private final JwtUtil jwtUtil;

    @Autowired
    protected TrainerServiceImpl(TrainerRepository trainerRepository,
                                 TrainingRepository trainingRepository,
                                 TrainingTypeRepository trainingTypeRepository,
                                 CredentialGenerator credentialGenerator,
                                 ConversionService conversionService, JwtUtil jwtUtil) {
        super(trainerRepository, conversionService);
        this.trainerRepository = trainerRepository;
        this.trainingRepository = trainingRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.credentialGenerator = credentialGenerator;
        this.conversionService = conversionService;
        this.jwtUtil = jwtUtil;
    }

    public UserDto register(RegisterTrainerDto request) {
        // Convert the specialization string to the corresponding enum
        String trainerSpecializationString = request.specialization();
        TrainingTypeEnum trainingTypeName = TrainingTypeEnum.valueOf(trainerSpecializationString);

        Trainer trainer = conversionService.convert(request, Trainer.class);
        trainer.setUsername(credentialGenerator.generateUsername(trainer.getFirstName(), trainer.getLastName()));
        trainer.setPassword(credentialGenerator.generatePassword());
        trainer.setSpecialization(trainingTypeRepository.findByTrainingTypeName(trainingTypeName));
        trainer.setActive(true);
        save(trainer);
        // generate token
        String token = jwtUtil.generateToken(trainer.getUsername());
        return new UserDto(trainer.getUsername(), token);
    }

    public TrainerDto update(String username, UpdateTrainerDto request) {
        // Convert the specialization string to the corresponding enum
        String trainerSpecializationString = request.specialization();
        TrainingTypeEnum trainerSpecializationEnum = TrainingTypeEnum.valueOf(trainerSpecializationString);

        Trainer oldTrainer = trainerRepository.findByUsername(username)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(ExceptionConstants.NOT_FOUND_MESSAGE, "Trainer", username)
                        )
                );
        Trainer newTrainer = conversionService.convert(request, Trainer.class);
        newTrainer.setSpecialization(trainingTypeRepository.findByTrainingTypeName(trainerSpecializationEnum));
        newTrainer.setUsername(username);
        oldTrainer.update(newTrainer);
        save(oldTrainer);
        return conversionService.convert(newTrainer, TrainerDto.class);
    }

    @Override
    public List<TrainerTrainingDto> getAllTrainingsByUsername(String trainerUsername, LocalDate from, LocalDate to, String traineeUsername) {
        if (!userRepository.existsByUsername(trainerUsername)){
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
    public List<TrainerDto> getByIsActiveTrueAndAssignedTraineesIsEmpty() {
        List<Trainer> allActiveUnAssignedTrainers = trainerRepository.findByIsActiveTrueAndAssignedUsersIsEmpty();
        return allActiveUnAssignedTrainers.stream()
                .map(trainer -> conversionService.convert(trainer, TrainerDto.class))
                .toList();
    }

    @Override
    protected Class<TrainerDto> getDtoClass() {
        return TrainerDto.class;
    }
}
