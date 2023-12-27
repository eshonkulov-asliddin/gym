package dev.gym.service.impl;

import dev.gym.repository.TraineeRepository;
import dev.gym.repository.TrainerRepository;
import dev.gym.repository.TrainingRepository;
import dev.gym.repository.model.Trainee;
import dev.gym.repository.model.Trainer;
import dev.gym.repository.model.Training;
import dev.gym.service.TrainingService;
import dev.gym.service.dto.AddTrainingDto;
import dev.gym.service.exception.NotFoundException;
import dev.gym.service.exception.util.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class TrainingServiceImpl extends AbstractCrudService<AddTrainingDto, Long, Training> implements TrainingService {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final ConversionService conversionService;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository,
                               TraineeRepository traineeRepository,
                               TrainerRepository trainerRepository,
                               @Qualifier("customConvertionService") ConversionService conversionService) {
        super(trainingRepository, conversionService);
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Class<AddTrainingDto> getDtoClass() {
        return AddTrainingDto.class;
    }

    @Override
    public void addTraining(AddTrainingDto request) {
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
    }
}
