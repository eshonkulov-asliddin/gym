package dev.gym.controller;

import dev.gym.repository.model.TrainingType;
import dev.gym.repository.model.enums.TrainingTypeEnum;
import dev.gym.service.TrainerService;
import dev.gym.service.dto.RegisterTrainerDto;
import dev.gym.service.dto.TrainerDto;
import dev.gym.service.dto.UpdateTrainerDto;
import dev.gym.service.dto.UserDto;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerControllerTest {

    @Mock
    private TrainerService trainerService;
    @InjectMocks
    private TrainerController trainerController;

    @BeforeEach
    void initialiseRestAssuredMockMvcStandalone() {
        RestAssuredMockMvc.standaloneSetup(trainerController);
        RestAssuredMockMvc.basePath = "/api/v1/trainers";
    }

    @Test
    void givenValidRequest_whenRegister_thenReturnCreated() {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTrainingTypeName(TrainingTypeEnum.STRENGTH);

        RegisterTrainerDto registerTrainerDto = new RegisterTrainerDto("John", "Doe", trainingType);
        UserDto userDto = mock(UserDto.class);

        when(userDto.username()).thenReturn("John.Doe");
        when(trainerService.register(registerTrainerDto)).thenReturn(userDto);

        given()
            .contentType(ContentType.JSON)
            .body(registerTrainerDto)
        .when()
            .post("/register")
        .then()
            .statusCode(201)
            .body("username", equalTo("John.Doe"));

    }

    @Test
    void givenInvalidRequest_whenRegister_thenBadRequest() {
        RegisterTrainerDto registerTrainerDto = mock(RegisterTrainerDto.class);

        given()
            .contentType(ContentType.JSON)
            .body(registerTrainerDto)
        .when()
            .post("/register")
        .then()
            .statusCode(400);
    }

    @Test
    void givenValidUsername_whenGetByUsername_thenReturnOk() {
        String username = "John.Doe";
        TrainerDto trainerDto = mock(TrainerDto.class);

        when(trainerDto.username()).thenReturn(username);
        when(trainerService.getByUsername(username)).thenReturn(Optional.of(trainerDto));

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/{username}", username)
        .then()
            .statusCode(200)
            .body("username", equalTo(username));
    }

    @Test
    void givenInvalidUsername_whenGetByUsername_thenReturnNotFound() {
        String username = "John.Doe";

        when(trainerService.getByUsername(username)).thenReturn(Optional.empty());

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/{username}", username)
        .then()
            .statusCode(404);
    }

    @Test
    void givenValidRequest_whenUpdate_thenSuccess() {
        String username = "John.Doe";

        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTrainingTypeName(TrainingTypeEnum.STRENGTH);

        UpdateTrainerDto updateTrainerDto = new UpdateTrainerDto("John", "Doe", trainingType, false);

        TrainerDto trainerDto = mock(TrainerDto.class);
        when(trainerDto.username()).thenReturn(username);
        when(trainerDto.isActive()).thenReturn(false);
        when(trainerService.update(username, updateTrainerDto)).thenReturn(trainerDto);

        given()
            .pathParam("username", username)
            .contentType(ContentType.JSON)
            .body(updateTrainerDto)
        .when()
            .put("/{username}", username)
        .then()
            .statusCode(200)
            .body("username", equalTo(username))
            .body("isActive", equalTo(false));
    }

    @Test
    void giveValidUsername_whenGetTrainings_thenSuccess() {
        String username = "John.Doe";

        given()
            .pathParam("username", username)
            .contentType(ContentType.JSON)
        .when()
            .get("/{username}/trainings", username)
        .then()
            .statusCode(200);
    }

    @Test
    void givenValidUsernameWithAcitiveStatusParam_whenSetActiveStatus_thenSuccess() {
        String username = "John.Doe";

        given()
            .pathParam("username", username)
            .queryParam("active_status", false)
            .contentType(ContentType.JSON)
        .when()
            .patch("/{username}", username)
        .then()
            .statusCode(200);
    }

    @Test
    void givenValidUsernameWithoutActiveStatus_whenSetActiveStatus_thenBadRequest() {
        String username = "John.Doe";

        given()
            .pathParam("username", username)
            .contentType(ContentType.JSON)
        .when()
            .patch("/{username}", username)
        .then()
            .statusCode(400);
    }
}
