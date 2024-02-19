package dev.gym.controller;

import dev.gym.GymApplication;
import dev.gym.controller.util.RestApiConst;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {GymApplication.class})
@AutoConfigureMockMvc
class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void withUnauthorizedUser_whenGetTrainingTypes_thenReturn401() throws Exception {
        this.mockMvc.perform(get(RestApiConst.TRAINING_API_ROOT_PATH + "/types"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "user")
    @Test
    void withAuthorizedUser_whenGetTrainingTypes_thenReturn200() throws Exception {
        this.mockMvc.perform(get(RestApiConst.TRAINING_API_ROOT_PATH + "/types"))
                .andExpect(status().isOk());
    }

    @Test
    void withUnauthorizedUser_whenGetProfile_thenReturn401() throws Exception {
        this.mockMvc.perform(get(RestApiConst.TRAINEE_API_ROOT_PATH + "/admin"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "admin")
    @Test
    void withAuthorizedUser_whenGetNonExistentProfile_thenReturn404() throws Exception {
        this.mockMvc.perform(get(RestApiConst.TRAINEE_API_ROOT_PATH + "/admin"))
                .andExpect(status().isNotFound());
    }
}
