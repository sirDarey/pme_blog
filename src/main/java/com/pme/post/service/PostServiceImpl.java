package com.pme.post.service;

import com.pme.post.dto.GetAPostResponseDTO;
import com.pme.post.dto.GetAllPostsResponseDTO;
import com.pme.post.dto.NewPostRequestDTO;
import com.pme.post.dto.PostInfo;
import com.pme.post.entity.Post;
import com.pme.post.repo.PostRepo;
import com.pme.user.entity.User;
import com.pme.user.repo.UserRepo;
import com.pme.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    @Autowired
    public PostServiceImpl(PostRepo postRepo, UserRepo userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    @Override
    public ResponseEntity<GetAllPostsResponseDTO> getAllPosts(int page, int limit) {
        //Setting Pagination Params
        page = Math.max(page, 1);
        limit = Math.max(limit, 1);

        //Fetching From Database
        Page<Post> allPosts = postRepo.findAll(PageRequest.of(page-1, limit)
                .withSort(Sort.by(Sort.Direction.DESC, "publicationDate"))); //Sort by publication_date

        //Extracting to DTO
        List<PostInfo> data = allPosts.stream()
                .map(post -> Utils.extractPostInfo(post))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(new GetAllPostsResponseDTO(
                "All Posts Retrieved Successfully", data ));
    }

    @Override
    public ResponseEntity<GetAPostResponseDTO> getAPost(Long postId) {
        //Fetching From Database
        Post post = postRepo.findById(postId).get();

        //Extracting to DTO
        PostInfo data = Utils.extractPostInfo(post);

        return ResponseEntity.ok().body(new GetAPostResponseDTO(
                "Post Retrieved Successfully", data ));
    }

    @Override
    public ResponseEntity<GetAllPostsResponseDTO> getAllPostsByUser(
            Long userId, int page, int limit, LocalDate start_date, LocalDate end_date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //Validating end_date param
        end_date = (end_date == null)? LocalDate.now() : end_date;

        //Validating start_date param
        if (start_date != null && (start_date.isAfter(LocalDate.now()) || start_date.isAfter(end_date)))
            return ResponseEntity.status(400).body(new GetAllPostsResponseDTO(
                    "Start Date MUST precede (or be equal to) End Date and(or) Today's Date",
                    null));

        //Setting Pagination Params
        page = Math.max(page, 1);
        limit = Math.max(limit, 1);
        int offset = (page-1)*limit;

        String responseMessage;
        List<PostInfo> data;

        //Setting Customized SQL Query to Populate 'data'; also customizing responseMessage
        if (start_date == null) {
            data = postRepo.getAllPostsByUser1(
                    userId, end_date.atStartOfDay().plusDays(1), limit, offset);
            responseMessage = "up until '"+end_date.format(formatter) +"'";
        } else {
            data = postRepo.getAllPostsByUser2(
                    userId, start_date.atStartOfDay(), end_date.atStartOfDay().plusDays(1), limit, offset);
            responseMessage = "From '"+start_date.format(formatter)+ "' To '"+end_date.format(formatter) +"'";
        }

        return ResponseEntity.ok().body(new GetAllPostsResponseDTO(
                "Retrieved Posts "+responseMessage, data ));
    }

    @Transactional
    @Override
    public ResponseEntity<GetAllPostsResponseDTO> addPost(NewPostRequestDTO newPostRequest, Long userId) throws AuthenticationException {
        if(userId == null)
            throw new AuthenticationException("User NOT Found");
        //Getting User with id = {userId}
        User user = userRepo.findById(userId).get();

        //Converting Request DTO to a Post Entity Object
        Post newPost = Utils.newPostSetter(newPostRequest);
        //Saving New Post By User to Database
        user.getPosts().add(newPost);

        //Getting all posts so far by this user and Extracting to DTO
        List<PostInfo> data = postRepo.getAllPostsByUser1(
                userId, LocalDateTime.now().plusDays(1), 10, 0);

        return ResponseEntity.status(201).body(new GetAllPostsResponseDTO(
           "New Post Created Successfully By User With id "+userId, data ));
    }
}