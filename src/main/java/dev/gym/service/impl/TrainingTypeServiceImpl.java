package dev.gym.service.impl;

import dev.gym.repository.TrainingTypeRepository;
import dev.gym.repository.model.TrainingType;
import dev.gym.service.TrainingTypeService;
import dev.gym.service.dto.TrainingTypeDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService<TrainingTypeDto> {

    private final TrainingTypeRepository trainingTypeRepository;
    private final ConversionService conversionService;

    public TrainingTypeServiceImpl(TrainingTypeRepository trainingTypeRepository,
                                   @Qualifier("customConvertionService") ConversionService conversionService) {
        this.trainingTypeRepository = trainingTypeRepository;
        this.conversionService = conversionService;
    }

    @Override
    public List<TrainingTypeDto> getAll() {
        List<TrainingType> all = trainingTypeRepository.findAll();
        return all.stream()
                .map(trainingType -> conversionService.convert(trainingType, TrainingTypeDto.class))
                .toList();
    }
}
