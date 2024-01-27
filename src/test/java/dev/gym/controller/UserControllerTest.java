package dev.gym.controller;

import dev.gym.service.impl.UserServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static dev.gym.controller.util.RestApiConst.USER_API_ROOT_PATH;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceImpl userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    void initialiseRestAssuredMockMvcStandalone() {
        RestAssuredMockMvc.standaloneSetup(userController);
        RestAssuredMockMvc.basePath = USER_API_ROOT_PATH;
    }

    @Test
    void givenValidUsernameAndPasswordWitNewPassword_whenUpdatePassword_thenSuccess() {
        when(passwordEncoder.encode("newPassword")).thenReturn("newPassword");
        doNothing().when(userService).updatePassword("John.Doe", "newPassword");

        given()
            .pathParam("username", "John.Doe")
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
