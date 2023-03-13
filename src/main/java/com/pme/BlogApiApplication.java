package com.pme;

import com.pme.post.dto.GetInitialPostsDTO;
import com.pme.post.entity.Post;
import com.pme.post.repo.PostRepo;
import com.pme.utils.Utils;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class BlogApiApplication {

	private final RestTemplate restTemplate;
	private final PostRepo postRepo;

	public BlogApiApplication(RestTemplate restTemplate, PostRepo postRepo) {
		this.restTemplate = restTemplate;
		this.postRepo = postRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}

	@PostConstruct
	public void importPostFromSecondBlog () {
		GetInitialPostsDTO allInitialPosts = null;
		//Connecting to the Given Endpoint for the Other Blog and Getting All Initial Posts
		try {
			allInitialPosts =
					restTemplate.getForObject("https://mocki.io/v1/d33691f7-1eb5-45aa-9642-8d538f6c5ebd",
							GetInitialPostsDTO.class);

			System.out.println("All Posts Imported Successfully");

		} catch (RestClientResponseException e) {
			System.out.println("Unable to Import Posts");

			Object o = e.getResponseBodyAs(Object.class);
			System.out.println(o.toString());
		}

		//Extracting response to DTO
		List<Post> posts = allInitialPosts.getData().stream()
				.map(post -> Utils.initialPostsSetter(post))
				.collect(Collectors.toList());

		//Saving to Database
		postRepo.saveAll(posts);
	}
}
