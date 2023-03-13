package com.pme.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor  @NoArgsConstructor
@Schema(description = "DTO Request Body for ADD New Post")
public class NewPostRequestDTO {

    @NotBlank(message = "Title Cannot be BLANK")
    @Size(min = 10, message = "Title too SHORT; min length is 10")
    private String title;

    @NotBlank(message = "Description Cannot be BLANK")
    @Size(min = 20, message = "Description too SHORT; min length is 20")
    private String description;
}
