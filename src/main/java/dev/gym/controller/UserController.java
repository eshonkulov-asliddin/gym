package dev.gym.controller;


import dev.gym.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static dev.gym.controller.util.RestApiConst.AUTHENTICATION_NAME;
import static dev.gym.controller.util.RestApiConst.USER_API_ROOT_PATH;

@RestController
@RequestMapping(path = USER_API_ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User API", description = "Operations to manage users")
@SecurityRequirement(name = AUTHENTICATION_NAME) // security requirement for swagger
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @PutMapping("/{username}/credentials")
    @Operation(summary = "Update user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> updatePassword(@PathVariable(name = "username") String username,
                               @RequestParam(name = "new_password") String newPassword) {
        String hashedPassword = passwordEncoder.encode(newPassword);
        userService.updatePassword(username, hashedPassword);
        return ResponseEntity.ok().build();
    }
}
