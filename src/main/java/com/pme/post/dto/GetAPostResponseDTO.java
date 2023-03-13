package com.pme.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor  @NoArgsConstructor
@Schema(description = "DTO Response Body for GET A Specific Post")
public class GetAPostResponseDTO {
    private String message;
    private PostInfo data;
}
