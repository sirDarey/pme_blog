package com.pme.user.service;

import com.pme.user.dto.GetUserResponseDTO;
import com.pme.user.dto.SignUpRequestDTO;
import com.pme.user.dto.VerifyEmailRequestDTO;
import org.springframework.http.ResponseEntity;

import java.net.ConnectException;

public interface UserService {
    ResponseEntity<?> registerUser(SignUpRequestDTO newUserRequest) throws ConnectException;

    ResponseEntity<GetUserResponseDTO> verifyEmail(VerifyEmailRequestDTO verifyEmailRequest);

//    ResponseEntity<String> login(LoginRequestDTO loginRequest) throws AuthenticationException;
}
