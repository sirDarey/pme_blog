package com.pme.post.service;

import com.pme.post.dto.GetAPostResponseDTO;
import com.pme.post.dto.GetAllPostsResponseDTO;
import com.pme.post.dto.NewPostRequestDTO;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.time.LocalDate;

public interface PostService {
    ResponseEntity<GetAllPostsResponseDTO> getAllPosts(int page, int limit);

    ResponseEntity<GetAPostResponseDTO> getAPost(Long postId);

    ResponseEntity<GetAllPostsResponseDTO> getAllPostsByUser(
            Long userId, int page, int limit, LocalDate start_date, LocalDate end_date);

    ResponseEntity<GetAllPostsResponseDTO> addPost(NewPostRequestDTO newPost, Long userId) throws AuthenticationException;
}
