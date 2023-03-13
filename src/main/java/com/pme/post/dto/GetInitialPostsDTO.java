package com.pme.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor  @NoArgsConstructor
@Schema(description = "DTO Response Body for GET All Initial Posts from other Blog")
public class GetInitialPostsDTO {
    private List<PostInfo> data;
}
