package dev.gym.controller;

import dev.gym.service.TraineeService;
import dev.gym.service.dto.RegisterTraineeDto;
import dev.gym.service.dto.TraineeDto;
import dev.gym.service.dto.TraineeTrainerDto;
import dev.gym.service.dto.TrainerDto;
import dev.gym.service.dto.UpdateTraineeDto;
import dev.gym.service.dto.UserDto;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeControllerTest {

    @Mock
    private TraineeService traineeService;
    @InjectMocks
    private TraineeController traineeController;

    @BeforeEach
    void initialiseRestAssuredMockMvcStandalone() {
        RestAssuredMockMvc.standaloneSetup(traineeController);
        RestAssuredMockMvc.basePath = "/api/v1/trainees";
    }

    @Test
    void givenValidCreateTraineeDto_whenRegister_thenReturnCreated() {

        RegisterTraineeDto registerTraineeDto = new RegisterTraineeDto("John", "Doe", null, null);

        String USERNAME = "John.Doe";
        UserDto userDto = mock(UserDto.class);
        when(userDto.username()).thenReturn(USERNAME);

        when(traineeService.register(registerTraineeDto)).thenReturn(userDto);

        given()
            .contentType(JSON)
            .body(registerTraineeDto)
        .when()
            .post("/register")
        .then()
            .log().ifValidationFails()
            .statusCode(201)
            .contentType(JSON)
            .body("username", equalTo(USERNAME));
    }

    @Test
    void givenInvalidDto_whenRegister_thenBadRequest() {
        RegisterTraineeDto registerTraineeDto = mock(RegisterTraineeDto.class);

        given()
            .contentType(JSON)
            .body(registerTraineeDto)
        .when()
            .post("/register")
        .then()
            .log().ifValidationFails()
            .statusCode(400);
    }

    @Test
    void givenValidUsername_whenGetByUsername_thenSuccess() {
        String USERNAME = "John.Doe";
        TraineeDto traineeDto = mock(TraineeDto.class);
        when(traineeDto.username()).thenReturn(USERNAME);

        when(traineeService.getByUsername(USERNAME)).thenReturn(Optional.of(traineeDto));

        given()
            .pathParam("username", USERNAME)
            .contentType(JSON)
        .when()
            .get("/{username}")
        .then()
            .statusCode(200)
            .contentType(JSON)
            .body("username", equalTo(USERNAME));
    }

    @Test
    void givenInvalidUsername_whenGetByUsername_thenNotFound() {
        String USERNAME = "Unknown";

        given()
            .pathParam("username", USERNAME)
            .contentType(JSON)
        .when()
            .get("/{username}")
        .then()
            .statusCode(404);
    }

    @Test
    void givenValidRequest_whenUpdate_thenSuccess() {
        String USERNAME = "John.Doe";

        UpdateTraineeDto updateTraineeDto = new UpdateTraineeDto("John", "Doe", null, null, true);
        TraineeDto traineeDto = mock(TraineeDto.class);

        when(traineeService.update(USERNAME, updateTraineeDto)).thenReturn(traineeDto);

        given()
            .contentType(JSON)
            .pathParam("username", USERNAME)
            .body(updateTraineeDto)
        .when()
            .put("/{username}")
        .then()
            .statusCode(200);
    }

    @Test
    void givenValidUsername_whenDelete_thenOk() {
        String USERNAME = "Jon.Doe";

        doNothing().when(traineeService).deleteByUsername(USERNAME);

        given()
            .contentType(JSON)
            .pathParam("username", USERNAME)
        .when()
            .delete("/{username}")
        .then()
            .statusCode(204);
    }

    @Test
    void givenValidUsernameAndTrainerDtoList_whenUpdateTrainers_thenSuccess() {
        String USERNAME = "John.Doe";
        TraineeTrainerDto traineeTrainerDto = new TraineeTrainerDto("trainerUsername");
        TrainerDto trainerDto = mock(TrainerDto.class);

        when(traineeService.updateTrainers(USERNAME, List.of(traineeTrainerDto))).thenReturn(List.of(trainerDto));

        given()
            .contentType(JSON)
            .pathParam("username", USERNAME)
            .body(List.of(traineeTrainerDto))
        .when()
            .put("/{username}/trainers")
        .then()
            .statusCode(200);
    }

    @Test
    void givenInvalidRequest_whenUpdateTrainers_thenBadRequest() {
        String USERNAME = "John.Doe";
        TraineeTrainerDto traineeTrainerDto = new TraineeTrainerDto(null);

        given()
            .contentType(JSON)
            .pathParam("username", USERNAME)
            .body(List.of(traineeTrainerDto))
        .when()
            .put("/{username}/trainers")
        .then()
            .statusCode(400);
    }

    @Test
    void givenValidRequest_whenGetTrainings_thenSuccess() {
        String USERNAME = "John.Doe";

        given()
            .contentType(JSON)
            .pathParam("username", USERNAME)
        .when()
            .get("/{username}/trainings")
        .then()
            .statusCode(200);
    }
}
