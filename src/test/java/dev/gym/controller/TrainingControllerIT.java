package dev.gym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gym.GymApplication;
import dev.gym.controller.util.RestApiConst;
import dev.gym.repository.model.enums.TrainingTypeEnum;
import dev.gym.service.dto.CreateTrainingDto;
import dev.gym.service.dto.RegisterTraineeDto;
import dev.gym.service.dto.RegisterTrainerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GymApplication.class)
@AutoConfigureMockMvc
class TrainingControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String traineeFirstName = "traineeFirstName";
    private static final String traineeLastName = "traineeLastName";
    private static final String trainerFirstName = "trainerFirstName";
    private static final String trainerLastName = "trainerLastName";

    @WithMockUser(username = "user")
    @Test
    void givenValidRequest_whenCreateTraining_thenCreated() throws Exception {
        createTrainee();
        createTrainer();

        CreateTrainingDto createTrainingDto = new CreateTrainingDto(
            traineeFirstName + "." + traineeLastName,
            trainerFirstName + "." + trainerLastName,
            "Test Training",
            LocalDate.now().plusDays(3),
            60
        );
        mockMvc.perform(post(RestApiConst.TRAINING_API_ROOT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTrainingDto)))
                .andExpect(status().isCreated());
    }


    void createTrainer() throws Exception {
        RegisterTrainerDto trainerDto = new RegisterTrainerDto(trainerFirstName, trainerLastName, TrainingTypeEnum.STRENGTH.toString());
        mockMvc.perform(post(RestApiConst.TRAINER_API_ROOT_PATH + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trainerDto)))
                .andExpect(status().isCreated());
    }

    void createTrainee() throws Exception {
        RegisterTraineeDto traineeDto = new RegisterTraineeDto(traineeFirstName, traineeLastName, LocalDate.now().minusYears(20), "Test Address");
        mockMvc.perform(post(RestApiConst.TRAINEE_API_ROOT_PATH + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(traineeDto)))
                .andExpect(status().isCreated());
    }
}
