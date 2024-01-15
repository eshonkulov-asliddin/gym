package dev.gym.controller;

import dev.gym.service.TrainingService;
import dev.gym.service.TrainingTypeService;
import dev.gym.service.dto.CreateTrainingDto;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDate;

import static dev.gym.controller.util.RestApiConst.TRAINING_API_ROOT_PATH;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;


@ExtendWith(MockitoExtension.class)
class TrainingControllerTest {

    @Mock
    private TrainingService trainingService;
    @Mock
    private TrainingTypeService trainingTypeService;
    @Mock
    private ConversionService conversionService;
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
}
