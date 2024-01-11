package dev.gym.controller;

import dev.gym.security.authentication.UserAuthService;
import dev.gym.service.impl.UserServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserAuthService userAuthService;
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    void initialiseRestAssuredMockMvcStandalone() {
        RestAssuredMockMvc.standaloneSetup(userController);
        RestAssuredMockMvc.basePath = "/api/v1/users";
    }

    @Test
    void givenValidUsernameAndPassword_whenLogin_thenSuccess() {

        doNothing().when(userAuthService).authenticate("John.Doe", "password");

        given()
            .param("username", "John.Doe")
            .param("password", "password")
        .when()
            .get("/login")
        .then()
            .log().ifValidationFails()
            .statusCode(200);
    }

    @Test
    void givenRequest_whenUsernameOrPasswordMissing_thenBadRequest() {

        given()
            .param("username", "John.Doe")
        .when()
            .get("/login")
        .then()
            .log().ifValidationFails()
            .statusCode(400);
    }

    @Test
    void givenValidUsernameAndPasswordWitNewPassword_whenUpdatePassword_thenSuccess() {
        doNothing().when(userAuthService).authenticate("John.Doe", "password");
        doNothing().when(userService).updatePassword("John.Doe", "newPassword");

        given()
            .pathParam("username", "John.Doe")
            .param("old_password", "password")
            .param("new_password", "newPassword")
        .when()
            .put("/{username}/credentials")
        .then()
            .log().ifValidationFails()
            .statusCode(200);
    }

    @Test
    void givenUsernameAndPasswordWithoutNewPassword_whenUpdatePassword_thenBadReqeust() {
        given()
            .pathParam("username", "John.Doe")
            .param("old_password", "password")
        .when()
            .put("/{username}/credentials")
        .then()
            .log().ifValidationFails()
            .statusCode(400);
    }
}
