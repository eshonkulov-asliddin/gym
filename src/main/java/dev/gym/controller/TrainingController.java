package dev.gym.controller;

import dev.gym.service.TrainingService;
import dev.gym.service.TrainingTypeService;
import dev.gym.service.dto.CreateTrainingDto;
import dev.gym.service.dto.TrainingTypeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static dev.gym.controller.util.RestApiConst.AUTHENTICATION_NAME;
import static dev.gym.controller.util.RestApiConst.TRAINING_API_ROOT_PATH;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = TRAINING_API_ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Training API", description = "Operations to manage trainings")
@SecurityRequirement(name = AUTHENTICATION_NAME) // security requirement for swagger
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingTypeService<TrainingTypeDto> trainingTypeService;

    @PostMapping
    @ResponseStatus(code = CREATED)
    @Operation(summary = "Create a new training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Training created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Training already exists")
    })
    public void addTraining(@RequestBody @Valid CreateTrainingDto request) {
        trainingService.addTraining(request);
    }

    @GetMapping("/types")
    @Operation(summary = "Get training types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Training types retrieved successfully")
    })
    public ResponseEntity<List<TrainingTypeDto>> getTrainingTypes() {
        List<TrainingTypeDto> trainingTypeDtoList = trainingTypeService.getAll();
        return ResponseEntity.ok(trainingTypeDtoList);
    }
}
