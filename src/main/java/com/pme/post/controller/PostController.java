package com.pme.post.controller;

import com.pme.post.dto.GetAPostResponseDTO;
import com.pme.post.dto.GetAllPostsResponseDTO;
import com.pme.post.dto.NewPostRequestDTO;
import com.pme.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.time.LocalDate;

@Tag(name = "POSTS CONTROLLER", description = "Endpoints For Posts Operations")
@RestController
@RequestMapping("/api/blog")
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @Operation(summary = "GET All Posts EndPoint. Anyone can access this. Posts are sorted by " +
            "publication_date in DESC order.  The Query params, 'page' and 'limit' are pagination " +
            "parameters. 'page' indicates the page number to be displayed while 'limit' indicates the " +
            "number of rows per page")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All Posts Retrieved Successfully"),
            })
    @GetMapping("/posts")
    public ResponseEntity<GetAllPostsResponseDTO> getAllPosts(
            @RequestParam(defaultValue = "1") int page, //Pagination Params
            @RequestParam(defaultValue = "10") int limit //Pagination Params
    ) {
        return postService.getAllPosts(page, limit);
    }

    @Operation(summary = "GET A Specific Posts EndPoint. Anyone can access this also. " +
            "'id' is a path parameter for the post id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post Retrieved Successfully"),
    })
    @GetMapping("/posts/{id}")
    public ResponseEntity<GetAPostResponseDTO> getAPost(@PathVariable(value = "id")  Long postId) {
        return postService.getAPost(postId);
    }

    @Operation(summary = "GET All Posts By a Signed-In User. Only Authorized user can access this.  " +
            "Thus, you have to enter a username(email) and password as a 'BASIC AUTH' type; " +
            "the user_id must also be passed as a request HEADER param" +

            "  The NON-Compulsory request params for this request are: " +
            " 'page' and 'limit' - for pagination " +
            " 'start_date' and 'end_date' - for sorting posts by 'publication date'." +
            "  The date params must follow the format: 'yyyy-MM-dd'" )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All Posts Retrieved Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request Params"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access; you need to Login")
    })
    @GetMapping("/users/posts")
    public ResponseEntity<GetAllPostsResponseDTO> getAllPostsByUser(
            @RequestHeader(name = "user_id")  Long userId,
            @RequestParam(defaultValue = "1") int page, //Pagination Param
            @RequestParam(defaultValue = "10") int limit, //Pagination Param
            @RequestParam(required = false) String start_date, //Sorting Param
            @RequestParam(required = false) String end_date //Sorting Param
    ) {

        LocalDate startDate = (start_date == null)? null : LocalDate.parse(start_date);
        LocalDate endDate = (end_date == null)? null : LocalDate.parse(end_date);

        return postService.getAllPostsByUser(userId, page, limit, startDate, endDate);
    }

    @Operation(summary = "ADD New Post By a Signed-In User. Only Authorized user can access this." +
            "  Thus, you have to enter a username(email) and password as a 'BASIC AUTH' type; " +
            "  the user_id must also be passed as a request HEADER param" +
            "  The sample Request Body needed is stated")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "New Post Created Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Json Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access; you need to Login")
    })
    @PostMapping("/posts")
    public ResponseEntity<GetAllPostsResponseDTO> addPost(
            @Valid @RequestBody NewPostRequestDTO newPostRequest, @RequestHeader(name = "user_id") Long userId) throws AuthenticationException {
        return postService.addPost(newPostRequest, userId);
    }
}
