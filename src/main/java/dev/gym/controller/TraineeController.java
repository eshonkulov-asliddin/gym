package dev.gym.controller;

import dev.gym.service.TraineeService;
import dev.gym.service.dto.RegisterTraineeDto;
import dev.gym.service.dto.TraineeDto;
import dev.gym.service.dto.TraineeTrainerDto;
import dev.gym.service.dto.TraineeTrainingDto;
import dev.gym.service.dto.TrainerDto;
import dev.gym.service.dto.UpdateTraineeDto;
import dev.gym.service.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static dev.gym.controller.util.RestApiConst.TRAINEE_API_ROOT_PATH;

@RestController
@RequestMapping(value = TRAINEE_API_ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Trainee API", description = "Operations to manage trainees")
public class TraineeController {

    private final TraineeService traineeService;

    @Autowired
    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainee registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterTraineeDto request) {
        UserDto registered = traineeService.register(request);
        return new ResponseEntity<>(registered, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{username}")
    @Operation(summary = "Get trainee profile by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    public ResponseEntity<TraineeDto> getByUsername(@PathVariable(name = "username") String username) {
        return ResponseEntity.of(traineeService.getByUsername(username));
    }

    @PutMapping("/{username}")
    @Operation(summary = "Update trainee profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    public ResponseEntity<TraineeDto> update(@PathVariable(name = "username") String username,
                                             @RequestBody @Valid UpdateTraineeDto request) {
        return ResponseEntity.ok(traineeService.update(username, request));
    }

    @DeleteMapping("/{username}")
    @Operation(summary = "Delete trainee profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trainee profile deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    public ResponseEntity<Void> delete(@PathVariable(name = "username") String username) {
        traineeService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{username}/trainers")
    @Operation(summary = "Update trainee's trainers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainees' trainers updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    public ResponseEntity<List<TrainerDto>> updateTrainers(@PathVariable(name = "username") String username,
                                                           @RequestBody @Valid List<TraineeTrainerDto> trainerDtoList) {
        List<TrainerDto> updatedTraineeTrainers = traineeService.updateTrainers(username, trainerDtoList);
        return ResponseEntity.ok(updatedTraineeTrainers);
    }

    @GetMapping("/{username}/trainings")
    @Operation(summary = "Get all trainings for a trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee's trainings retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    public ResponseEntity<List<TraineeTrainingDto>> getTrainings(
            @PathVariable(name = "username") String username,
            @RequestParam(name = "period_from", required = false) LocalDate periodFrom,
            @RequestParam(name = "period_to", required = false) LocalDate periodTo,
            @RequestParam(name = "trainer_username", required = false) String trainerUsername,
            @RequestParam(name = "training_type", required = false) String trainingType) {

        List<TraineeTrainingDto> allTrainingsByUsername = traineeService.getAllTrainingsByUsername(username, periodFrom, periodTo, trainerUsername, trainingType);
        return ResponseEntity.ok(allTrainingsByUsername);

    }

    @PatchMapping("/{username}")
    @Operation(summary = "Set trainee active status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee's active status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    public ResponseEntity<Void> setActiveStatus(@PathVariable(name = "username") String username,
                                                @RequestParam(name = "active_status") boolean activeStatus) {
        traineeService.setActiveStatus(username, activeStatus);
        return ResponseEntity.ok().build();
    }
}
