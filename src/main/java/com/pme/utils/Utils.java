package com.pme.utils;

import com.pme.post.dto.NewPostRequestDTO;
import com.pme.post.dto.PostInfo;
import com.pme.post.entity.Post;
import com.pme.user.dto.SignUpRequestDTO;
import com.pme.user.dto.UserInfo;
import com.pme.user.entity.User;
import com.pme.user.repo.UserRepo;

import java.time.LocalDateTime;

public class Utils {
    public static PostInfo extractPostInfo(Post post) {
        return new PostInfo(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getPublicationDate()
        );
    }

    public static Post newPostSetter(NewPostRequestDTO newPostRequest) {
        return new Post(
                null,
                newPostRequest.getTitle(),
                newPostRequest.getDescription(),
                LocalDateTime.now(),
                "local");
    }

    public static User newUserSetter(SignUpRequestDTO signUpRequest) {
        return User.builder()
                    .email(signUpRequest.getEmail())
                    .firstName(signUpRequest.getFirstName())
                    .lastName(signUpRequest.getLastName())
                    .role("ROLE_USER")
                    .password(signUpRequest.getPassword())
                    .build();
    }

    public static UserInfo extractUserInfo(User savedUser) {
        return new UserInfo(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getPosts()
        );
    }

    public static boolean checkIfEmailExists(String email, UserRepo userRepo) {
        return userRepo.existsByEmail(email);
    }

    public static Post initialPostsSetter(PostInfo postInfo) {
        return new Post(
                null,
                postInfo.getTitle(),
                postInfo.getDescription(),
                postInfo.getPublication_date(),
                "imported");
    }
}
