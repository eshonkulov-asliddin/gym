package dev.gym.controller;

import dev.gym.service.TrainerService;
import dev.gym.service.dto.RegisterTrainerDto;
import dev.gym.service.dto.TrainerDto;
import dev.gym.service.dto.TrainerTrainingDto;
import dev.gym.service.dto.UpdateTrainerDto;
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

import static dev.gym.controller.util.RestApiConst.TRAINER_API_ROOT_PATH;

@RestController
@RequestMapping(value = TRAINER_API_ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Trainer API", description = "Operations to manage trainers")
public class TrainerController {

    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainer registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Trainer already exists")
    })
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterTrainerDto request) {
        return new ResponseEntity<>(trainerService.register(request), HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    @Operation(summary = "Get trainer profile by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer profile found"),
            @ApiResponse(responseCode = "404", description = "Trainer not found")
    })
    public ResponseEntity<TrainerDto> getByUsername(@PathVariable(name = "username") String username) {
        return ResponseEntity.of(trainerService.getByUsername(username));
    }

    @GetMapping("/unassigned")
    @Operation(summary = "Get all unassigned trainers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainers found")
    })
    public ResponseEntity<List<TrainerDto>> getUnassignedTrainers(@RequestParam(name = "username") String username) {
        return ResponseEntity.ok(trainerService.getAllActiveUnAssignedTrainers());
    }


    @PutMapping("/{username}")
    @Operation(summary = "Update trainer profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Trainer not found")
    })
    public ResponseEntity<TrainerDto> update(@PathVariable(name = "username") String username,
                                             @RequestBody @Valid UpdateTrainerDto request) {
        return ResponseEntity.ok(trainerService.update(username, request));
    }

    @GetMapping("/{username}/trainings")
    @Operation(summary = "Get all trainings for a trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainings found"),
            @ApiResponse(responseCode = "404", description = "Trainer not found")
    })
    public ResponseEntity<List<TrainerTrainingDto>> getTrainings(
            @PathVariable(name = "username") String username,
            @RequestParam(name = "period_from", required = false) LocalDate periodFrom,
            @RequestParam(name = "period_to", required = false) LocalDate periodTo,
            @RequestParam(name = "traineeUsername", required = false) String traineeUsername) {
        List<TrainerTrainingDto> allTrainingsByUsername = trainerService.getAllTrainingsByUsername(username, periodFrom, periodTo, traineeUsername);
        return ResponseEntity.ok(allTrainingsByUsername);
    }

    @PatchMapping("/{username}")
    @Operation(summary = "Set trainer active status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer active status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Trainer not found")
    })
    public ResponseEntity<Void> setActiveStatus(@PathVariable(name = "username") String username,
                                                @RequestParam(name = "active_status") boolean activeStatus) {
        trainerService.setActiveStatus(username, activeStatus);
        return ResponseEntity.ok().build();
    }
}
