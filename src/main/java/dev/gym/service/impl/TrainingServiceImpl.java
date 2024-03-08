package dev.gym.service.impl;

import dev.gym.rabbitmq.WorkloadProducer;
import dev.gym.repository.TraineeRepository;
import dev.gym.repository.TrainerRepository;
import dev.gym.repository.TrainingRepository;
import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;
import dev.gym.service.TrainingService;
import dev.gym.service.client.workload.ActionType;
import dev.gym.service.client.workload.TrainerWorkload;
import dev.gym.service.dto.CreateTrainingDto;
import dev.gym.service.exception.NotFoundException;
import dev.gym.service.exception.util.ExceptionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrainingServiceImpl extends AbstractCrudService<CreateTrainingDto, Long, Training> implements TrainingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingServiceImpl.class);
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final ConversionService conversionService;
    private final WorkloadProducer workloadProducer;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository,
                               TraineeRepository traineeRepository,
                               TrainerRepository trainerRepository,
                               ConversionService conversionService,
                               WorkloadProducer workloadProducer) {
        super(trainingRepository, conversionService);
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.conversionService = conversionService;
        this.workloadProducer = workloadProducer;
    }

    @Override
    protected Class<CreateTrainingDto> getDtoClass() {
        return CreateTrainingDto.class;
    }

    @Override
    @Transactional
    public void addTraining(CreateTrainingDto request) {
        String traineeUsername = request.traineeUsername();
        String trainerUsername = request.trainerUsername();

        Trainee trainee = traineeRepository.findByUsername(traineeUsername)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(
                                        ExceptionConstants.NOT_FOUND_MESSAGE, "Trainee", traineeUsername
                                )
                        )
                );

        Trainer trainer = trainerRepository.findByUsername(trainerUsername)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(
                                        ExceptionConstants.NOT_FOUND_MESSAGE, "Trainer", trainerUsername
                                )
                        )
                );

        Training training = conversionService.convert(request, Training.class);
        training.setTrainee(trainee);
        training.setTrainingType(trainer.getSpecialization());
        training.setTrainer(trainer);
        save(training);

        TrainerWorkload trainerWorkload = new TrainerWorkload(trainer.getUsername(), trainer.getFirstName(), trainer.getLastName(), trainer.isActive(),
                training.getTrainingDate(), request.trainingDuration(), ActionType.ADD);
        notifyWorkloadService(trainerWorkload);
    }

    protected void notifyWorkloadService(TrainerWorkload trainerWorkload) {
        workloadProducer.send(trainerWorkload);
        LOGGER.info("Trainer workload sent to the queue: {}", trainerWorkload);
    }
}
