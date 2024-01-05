package dev.gym.controller;


import dev.gym.controller.util.RestApiConst;
import dev.gym.security.authentication.UserAuthService;
import dev.gym.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = RestApiConst.USER_API_ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User API", description = "Operations to manage users")
@RequiredArgsConstructor
public class UserController {

    private final UserAuthService userAuthService;
    private final UserServiceImpl userService;

    @GetMapping("/login")
    @Operation(summary = "Authenticate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> login(@RequestParam(name = "username") String username,
                                @RequestParam(name = "password") String password) {
        userAuthService.authenticate(username, password);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{username}/credentials")
    @Operation(summary = "Update user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> updatePassword(@PathVariable(name = "username") String username,
                               @RequestParam(name = "old_password") String oldPassword,
                               @RequestParam(name = "new_password") String newPassword) {
        userAuthService.authenticate(username, oldPassword);
        userService.updatePassword(username, newPassword);
        return ResponseEntity.ok().build();
    }
}
