package com.pme.user.dto;

import com.pme.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Schema(description = "'data' of DTO Response Body for User Details")
public class UserInfo {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Post> posts;
}
