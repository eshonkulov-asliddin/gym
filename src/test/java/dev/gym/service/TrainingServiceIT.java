package dev.gym.service;

import dev.gym.config.AppConfig;
import dev.gym.model.Trainee;
import dev.gym.model.Trainer;
import dev.gym.model.Training;
import dev.gym.repository.UserRepository;
import dev.gym.service.dto.TraineeDtoRequest;
import dev.gym.service.dto.TraineeDtoResponse;
import dev.gym.service.dto.TrainerDtoRequest;
import dev.gym.service.dto.TrainerDtoResponse;
import dev.gym.service.dto.TrainingDtoReponse;
import dev.gym.service.dto.TrainingDtoRequest;
import dev.gym.service.dto.TrainingTypeDto;
import dev.gym.service.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(classes = AppConfig.class)
class TrainingServiceIT {

    @Autowired
    private final UserRepository<Trainee, Training, Long> traineeRepository;

    @Autowired
    private final UserRepository<Trainer, Training, Long> trainerRepository;

    @Autowired
    private final CrudService<TrainingDtoRequest, TrainingDtoReponse, Long> trainingService;

    @Autowired
    private final UserMapper<TraineeDtoRequest, TraineeDtoResponse, Trainee> traineeMapper;

    @Autowired
    private final UserMapper<TrainerDtoRequest, TrainerDtoResponse, Trainer> trainerMapper;

    @Autowired
    TrainingServiceIT(UserRepository<Trainee, Training, Long> traineeRepository,
                      UserRepository<Trainer, Training, Long> trainerRepository,
                      CrudService<TrainingDtoRequest, TrainingDtoReponse, Long> trainingService,
                      UserMapper<TraineeDtoRequest, TraineeDtoResponse, Trainee> traineeMapper,
                      UserMapper<TrainerDtoRequest, TrainerDtoResponse, Trainer> trainerMapper) {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingService = trainingService;
        this.traineeMapper = traineeMapper;
        this.trainerMapper = trainerMapper;
    }

    @Test
    void testCRUD() {
        // create Trainee
        Trainee trainee = createTrainee();

        // create Trainer
        Trainer trainer = createTrainer();

        // create TrainingType
        TrainingTypeDto trainingTypeDto = new TrainingTypeDto("STRENGTH");

        String trainingName = "Test Training";
        LocalDate trainingDate = LocalDate.of(2021, 1, 1);
        int duration = 60;


        TrainingDtoRequest trainingDtoRequest = new TrainingDtoRequest(trainee.getId(), trainer.getId(), trainingName, trainingTypeDto, trainingDate, duration);

        // Save the training
        TrainingDtoReponse trainingDtoReponse = trainingService.save(trainingDtoRequest);

        assertNotNull(trainingDtoReponse);
    }

    private Trainer createTrainer() {
        String firstName = "John";
        String lastName = "Doe";

        TrainingTypeDto specialization = new TrainingTypeDto("STRENGTH");

        TrainerDtoRequest trainerDtoRequest = new TrainerDtoRequest(firstName, lastName, specialization);

        Trainer trainer = trainerMapper.dtoToModel(trainerDtoRequest);

        return trainerRepository.save(trainer);
    }

    private Trainee createTrainee() {
        String firstName = "John";
        String lastName = "Doe";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        String address = "123 Main St.";

        TraineeDtoRequest traineeDtoRequest = new TraineeDtoRequest(firstName, lastName, dateOfBirth, address);

        Trainee trainee = traineeMapper.dtoToModel(traineeDtoRequest);

        return traineeRepository.save(trainee);
    }

    @Test
    void givenWrongArguments_whenCreate_thenThrowIllegalArgumentException() {
        // create trainingDtoRequest
        TrainingDtoRequest trainingDtoRequest = new TrainingDtoRequest(null, null, null, null, null, 0);

        // Assert the training when fields are null
        assertThrows(IllegalArgumentException.class,
                () -> trainingService.save(trainingDtoRequest)
        );

    }

    @Test
    void givenWrongId_whenFindById_thenReturnOptionalEmpty() {
        assertEquals(Optional.empty(), trainingService.getById(null));
    }

}
