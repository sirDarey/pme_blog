package com.pme.user.service;

import com.pme.token.repo.TokenRepo;
import com.pme.token.service.TokenService;
import com.pme.user.dto.FailedRegistrationDTO;
import com.pme.user.dto.GetUserResponseDTO;
import com.pme.user.dto.SignUpRequestDTO;
import com.pme.user.dto.VerifyEmailRequestDTO;
import com.pme.user.entity.User;
import com.pme.user.repo.UserRepo;
import com.pme.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepo tokenRepo;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, TokenService tokenService, PasswordEncoder passwordEncoder,
                           TokenRepo tokenRepo, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("User NOT FOUND in the Database");

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getAuthorities().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        });

        return new org.springframework.security.core.userdetails.User (
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    @Transactional(rollbackFor = ConnectException.class)
    @Override
    public ResponseEntity<?> registerUser(SignUpRequestDTO newUserRequest) throws ConnectException {
        if (Utils.checkIfEmailExists(newUserRequest.getEmail(), userRepo))
            return ResponseEntity.status(400).body(new FailedRegistrationDTO(
                    "Email is Already taken", newUserRequest
            ));

        HttpStatusCode otpResponse = tokenService.sendOTP(newUserRequest.getEmail());
        newUserRequest.setPassword(passwordEncoder.encode(newUserRequest.getPassword()));
        User savedUser = userRepo.save(Utils.newUserSetter(newUserRequest));

        if(otpResponse.is2xxSuccessful())
            return ResponseEntity.status(201).body(new GetUserResponseDTO(
                    "Registration Successful", Utils.extractUserInfo(savedUser)
            ));

        throw new ConnectException();
    }

    @Transactional
    @Override
    public ResponseEntity<GetUserResponseDTO> verifyEmail(VerifyEmailRequestDTO verifyEmailRequest) {
        String email = verifyEmailRequest.getEmail();
        User user = userRepo.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("User NOT FOUND in the Database");

        ResponseEntity<String> verifyResponse =
                tokenService.validateOTP(email, verifyEmailRequest.getOTP(), tokenRepo);

        int statusCode = verifyResponse.getStatusCode().value();
        if (statusCode == 200) {
            user.setUserEnabled(true);
            tokenRepo.deleteByFactor(email);
        }

        return ResponseEntity.status(statusCode)
                .body(new GetUserResponseDTO(verifyResponse.getBody(),
                        Utils.extractUserInfo(user)));
    }
//
//    @Override
//    public ResponseEntity<String> login(LoginRequestDTO loginRequest) throws AuthenticationException {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginRequest.getUsername(),
//                            loginRequest.getPassword()
//                    )
//            );
//        }catch (Exception e) {
//            throw new AuthenticationException();
//        }
//
//        User user = userRepo.findByEmail(loginRequest.getUsername());
//        if (!user.isUserEnabled())
//            return ResponseEntity.status(401).body("Your Email has NOT been Verified");
//
//
//        return ResponseEntity.status(200).body("Login Successful");
//    }
}
