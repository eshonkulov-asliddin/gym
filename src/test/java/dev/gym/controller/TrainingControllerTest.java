package dev.gym.controller;

import dev.gym.client.workload.ActionType;
import dev.gym.client.workload.TrainerWorkload;
import dev.gym.client.workload.TrainerWorkloadClient;
import dev.gym.repository.model.TrainingType;
import dev.gym.service.TrainerService;
import dev.gym.service.TrainingService;
import dev.gym.service.TrainingTypeService;
import dev.gym.service.dto.CreateTrainingDto;
import dev.gym.service.dto.TrainerDto;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static dev.gym.controller.util.RestApiConst.TRAINING_API_ROOT_PATH;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TrainingControllerTest {

    @Mock
    private TrainingService trainingService;
    @Mock
    private TrainerService trainerService;
    @Mock
    private TrainingTypeService trainingTypeService;
    @Mock
    private TrainerWorkloadClient trainerWorkloadClient;
    @Mock
    private HttpServletRequest httpServletRequest;
    @InjectMocks
    private TrainingController trainingController;

    @BeforeEach
    void initialiseRestAssuredMockMvcStandalone() {
        RestAssuredMockMvc.standaloneSetup(trainingController);
        RestAssuredMockMvc.basePath = TRAINING_API_ROOT_PATH;
    }

    @Test
    void givenValidRequest_whenAddTraining_thenSuccess() {

        CreateTrainingDto createTrainingDto = new CreateTrainingDto("John.Doe", "Jane.Doe", "Yoga", LocalDate.now().plusDays(1), 60);

        given()
            .contentType(JSON)
            .body(createTrainingDto)
        .when()
            .post()
        .then()
            .log().ifValidationFails()
            .statusCode(200);
    }

    @Test
    void giveInvalidRequest_whenAddTraining_thenBadRequest() {

        given()
            .contentType(JSON)
        .when()
            .post()
        .then()
            .log().ifValidationFails()
            .statusCode(400);
    }

    @Test
    void givenValidRequest_whenGetTrainingTypes_thenSuccess() {

        given()
            .contentType(JSON)
        .when()
            .get("/types")
        .then()
            .log().ifValidationFails()
            .statusCode(200);
    }

    @Test
    void testNotifyWorkloadService_AddAction() {
        // set httpServletRequest to be used in notifyWorkloadService
        trainingController.setHttpServletRequest(httpServletRequest);

        CreateTrainingDto trainingDto = new CreateTrainingDto("traineeUsername", "trainerUsername", "Test Training", LocalDate.now().plusDays(2), 60);
        ActionType actionType = ActionType.ADD;

        TrainingType specialization = mock(TrainingType.class);
        TrainerDto trainerDto = new TrainerDto("username", "firstName", "lastName", specialization, true, List.of());

        when(trainerService.getByUsername(anyString())).thenReturn(Optional.of(trainerDto));
        when(httpServletRequest.getHeader("Authorization")).thenReturn("token");

        trainingController.notifyWorkloadService(trainingDto, actionType);

        verify(trainerService, times(1)).getByUsername("trainerUsername");
        verify(trainerWorkloadClient, times(1)).notifyWorkloadService(eq("token"), any(TrainerWorkload.class));
    }

    @Test
    void testNotifyWorkloadService_RemoveAction() {
        // set httpServletRequest to be used in notifyWorkloadService
        trainingController.setHttpServletRequest(httpServletRequest);

        CreateTrainingDto trainingDto = new CreateTrainingDto("traineeUsername", "trainerUsername", "Test Training", LocalDate.now().plusDays(2),  60);
        ActionType actionType = ActionType.DELETE;

        TrainingType specialization = mock(TrainingType.class);
        TrainerDto trainerDto = new TrainerDto("username", "firstName", "lastName", specialization, true, List.of());
        when(trainerService.getByUsername(anyString())).thenReturn(Optional.of(trainerDto));
        when(httpServletRequest.getHeader("Authorization")).thenReturn("token");

        trainingController.notifyWorkloadService(trainingDto, actionType);

        verify(trainerService, times(1)).getByUsername("trainerUsername");
        verify(trainerWorkloadClient, times(1)).notifyWorkloadService(eq("token"), any(TrainerWorkload.class));
    }

    @Test
    void whenBadRequest_thenDontCallTrainerWorkloadClient() {
        given()
                .contentType(JSON)
                .when()
                .post()
                .then()
                .log().ifValidationFails()
                .statusCode(400);

        verify(trainerWorkloadClient, never()).notifyWorkloadService(anyString(), any(TrainerWorkload.class));
    }
}
