package dev.gym.service.impl;

import dev.gym.service.client.workload.ActionType;
import dev.gym.service.client.workload.TrainerWorkload;
import dev.gym.service.client.workload.TrainerWorkloadClient;
import dev.gym.repository.TraineeRepository;
import dev.gym.repository.TrainerRepository;
import dev.gym.repository.TrainingRepository;
import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;
import dev.gym.security.credential.CredentialProvider;
import dev.gym.service.TrainingService;
import dev.gym.service.dto.CreateTrainingDto;
import dev.gym.service.exception.NotFoundException;
import dev.gym.service.exception.util.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrainingServiceImpl extends AbstractCrudService<CreateTrainingDto, Long, Training> implements TrainingService {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final ConversionService conversionService;
    private final TrainerWorkloadClient trainerWorkloadClient;
    private final CredentialProvider credentialProvider;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository,
                               TraineeRepository traineeRepository,
                               TrainerRepository trainerRepository,
                               ConversionService conversionService,
                               TrainerWorkloadClient trainerWorkloadClient,
                               CredentialProvider credentialProvider) {
        super(trainingRepository, conversionService);
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.conversionService = conversionService;
        this.trainerWorkloadClient = trainerWorkloadClient;
        this.credentialProvider = credentialProvider;
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
        String credential = credentialProvider.getCredential();
        trainerWorkloadClient.notifyWorkloadService(credential, trainerWorkload);
    }
}
