package com.pme.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Schema(description = "DTO Request Body for New User Registration")
public class SignUpRequestDTO {
    @NotBlank(message = "firstName Cannot be BLANK")
    @Size(min = 3, message = "min length allowed is: 3")
    private String firstName;
    @NotBlank(message = "lastName Cannot be BLANK")
    @Size(min = 3, message = "min length allowed is: 3")
    private String lastName;
    @NotBlank(message = "email Cannot be BLANK")
    private String email;
    @Size(min = 6, message = "min length allowed is: 6")
    @NotBlank(message = "password Cannot be BLANK")
    private String password;
}
