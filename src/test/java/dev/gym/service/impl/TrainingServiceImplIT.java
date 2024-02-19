package dev.gym.service.impl;

import dev.gym.repository.TraineeRepository;
import dev.gym.repository.TrainerRepository;
import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.enums.TrainingTypeEnum;
import dev.gym.security.credential.CredentialGenerator;
import dev.gym.service.TrainingService;
import dev.gym.service.dto.CreateTrainingDto;
import dev.gym.service.dto.RegisterTraineeDto;
import dev.gym.service.dto.RegisterTrainerDto;
import dev.gym.service.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TrainingServiceImplIT {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingService trainingService;
    private final CredentialGenerator credentialGenerator;
    private final ConversionService conversionService;

    @Autowired
    TrainingServiceImplIT(TraineeRepository traineeRepository,
                          TrainerRepository trainerRepository,
                          TrainingService trainingService,
                          CredentialGenerator credentialGenerator,
                          ConversionService conversionService) {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingService = trainingService;
        this.credentialGenerator = credentialGenerator;
        this.conversionService = conversionService;
    }

    @Test
    @WithMockUser(username = "user", password = "token")
    void testAddTraining() {
        // create Trainee
        Trainee trainee = createTrainee();

        // create Trainer
        Trainer trainer = createTrainer();

        String trainingName = randomAlphabetic(10);
        LocalDate trainingDate = LocalDate.of(2021, 1, 1);
        int duration = 60;


        CreateTrainingDto createTrainingDto = new CreateTrainingDto(trainee.getUsername(), trainer.getUsername(), trainingName, trainingDate, duration);

        // Save the training
        trainingService.addTraining(createTrainingDto);
    }

    private Trainer createTrainer() {
        String firstName = randomAlphabetic(10);
        String lastName = randomAlphabetic(10);

        RegisterTrainerDto trainerCreateDtoRequest = new RegisterTrainerDto(firstName, lastName, TrainingTypeEnum.STRENGTH.toString());
        Trainer trainer = conversionService.convert(trainerCreateDtoRequest, Trainer.class);
        trainer.setUsername(credentialGenerator.generateUsername(firstName, lastName));
        trainer.setPassword(credentialGenerator.generatePassword());
        trainerRepository.save(trainer);
        return trainerRepository.findByUsername(trainer.getUsername()).orElseThrow(() -> new NotFoundException("Trainer not found"));
    }

    private Trainee createTrainee() {
        String firstName = randomAlphabetic(10);
        String lastName = randomAlphabetic(10);
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        String address = randomAlphabetic(10);

        RegisterTraineeDto traineeCreateDtoRequest = new RegisterTraineeDto(firstName, lastName, dateOfBirth, address);
        Trainee trainee = conversionService.convert(traineeCreateDtoRequest, Trainee.class);
        trainee.setUsername(credentialGenerator.generateUsername(firstName, lastName));
        trainee.setPassword(credentialGenerator.generatePassword());
        traineeRepository.save(trainee);
        return traineeRepository.findByUsername(trainee.getUsername()).orElseThrow(() -> new NotFoundException("Trainee not found"));
    }

    @Test
    void givenWrongId_whenFindById_thenReturnOptionalEmpty() {
        assertEquals(Optional.empty(), trainingService.getById(null));
    }
}
