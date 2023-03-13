package com.pme.user.controller;

import com.pme.user.dto.GetUserResponseDTO;
import com.pme.user.dto.SignUpRequestDTO;
import com.pme.user.dto.VerifyEmailRequestDTO;
import com.pme.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.ConnectException;

@RestController
@RequestMapping("/api/blog/users")
@Tag(name = "USERS CONTROLLER", description = "Endpoints For Users' Operations")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "EndPoint For New User Registration.")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Email is Already taken; Response Body " +
                    "is of type: 'FailedRegistrationDTO'"),
            @ApiResponse(responseCode = "5xx", description = "Email OTP Service Failed; " +
                    "thus, OTP will NOT be sent, Registration FAILED"),
            @ApiResponse(responseCode = "201", description = "Registration SUCCESSFUL" +
                    "Response Body is of type: 'GetUserResponseDTO'"),
    })
    @PostMapping
    public ResponseEntity<?> registerUser (@Valid @RequestBody SignUpRequestDTO newUserRequest) throws ConnectException {
        return userService.registerUser(newUserRequest);
    }

    @Operation(summary = "EndPoint For Verifying Email via OTP.")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "User NOT FOUND"),
            @ApiResponse(responseCode = "200", description = "OTP Verification Successful"),
            @ApiResponse(responseCode = "401", description = "Invalid OTP"),
    })
    @PostMapping("/verify_email")
    public ResponseEntity<GetUserResponseDTO> verifyEmail (@RequestBody VerifyEmailRequestDTO verifyEmailRequest) {
        return userService.verifyEmail(verifyEmailRequest);
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login (@RequestBody LoginRequestDTO loginRequest) throws AuthenticationException {
//        return userService.login(loginRequest);
//    }
}
