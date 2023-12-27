package dev.gym.service;

import dev.gym.repository.TraineeRepository;
import dev.gym.repository.TrainerRepository;
import dev.gym.repository.config.RepositoryConfig;
import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.TrainingType;
import dev.gym.repository.model.enums.TrainingTypeEnum;
import dev.gym.security.config.SecurityConfig;
import dev.gym.service.config.ServiceConfig;
import dev.gym.service.dto.AddTrainingDto;
import dev.gym.service.dto.RegisterTraineeDto;
import dev.gym.service.dto.RegisterTrainerDto;
import dev.gym.service.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = {ServiceConfig.class, RepositoryConfig.class, SecurityConfig.class})
class TrainingServiceImplIT {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingService trainingService;
    private final ConversionService conversionService;

    @Autowired
    TrainingServiceImplIT(TraineeRepository traineeRepository,
                          TrainerRepository trainerRepository,
                          TrainingService trainingService, ConversionService conversionService) {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingService = trainingService;
        this.conversionService = conversionService;
    }


    @Test
    void testCRUD() {
        // create Trainee
        Trainee trainee = createTrainee();

        // create Trainer
        Trainer trainer = createTrainer();

        // create TrainingType
        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName(TrainingTypeEnum.CARDIO);

        String trainingName = "Test Training";
        LocalDate trainingDate = LocalDate.of(2021, 1, 1);
        int duration = 60;


        AddTrainingDto addTrainingDto = new AddTrainingDto(trainee.getUsername(), trainer.getUsername(), trainingName, trainingDate, duration);

        // Save the training
        trainingService.addTraining(addTrainingDto);

    }

    private Trainer createTrainer() {
        String firstName = "John";
        String lastName = "Doe";

        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName(TrainingTypeEnum.CARDIO);

        RegisterTrainerDto trainerCreateDtoRequest = new RegisterTrainerDto(firstName, lastName, trainingType);
        Trainer converted = conversionService.convert(trainerCreateDtoRequest, Trainer.class);
        trainerRepository.save(converted);
        return trainerRepository.findByUsername(converted.getUsername()).orElseThrow(() -> new NotFoundException("Trainer not found"));
    }

    private Trainee createTrainee() {
        String firstName = "John";
        String lastName = "Doe";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        String address = "123 Main St.";

        RegisterTraineeDto traineeCreateDtoRequest = new RegisterTraineeDto(firstName, lastName, dateOfBirth, address);
        Trainee trainee = conversionService.convert(traineeCreateDtoRequest, Trainee.class);
        traineeRepository.save(trainee);
        return traineeRepository.findByUsername(trainee.getUsername()).orElseThrow(() -> new NotFoundException("Trainee not found"));
    }

    @Test
    void givenWrongId_whenFindById_thenReturnOptionalEmpty() {
        assertEquals(Optional.empty(), trainingService.getById(null));
    }

}
