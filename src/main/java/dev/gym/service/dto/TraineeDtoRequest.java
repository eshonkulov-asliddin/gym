package dev.gym.service.dto;

import java.time.LocalDate;

public record TraineeDtoRequest(String firstName, String lastName, LocalDate dateOfBirth, String address) { }
