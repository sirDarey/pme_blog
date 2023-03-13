package com.pme.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor  @NoArgsConstructor @Builder
@Schema(description = "'data' of DTO Response Body for Post(s)")
public class PostInfo {
    private Long id;
    private String title;
    private String description;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime publication_date;
}
