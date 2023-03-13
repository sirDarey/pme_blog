package com.pme.config;

import com.pme.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

	private final UserRepo userRepo;

	@Autowired
	public SecurityConfig(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		String [] swaggerDocsPatterns = {
				"/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
		};

		http
				.csrf().disable()
				.authorizeHttpRequests()
				.requestMatchers(swaggerDocsPatterns).permitAll()
				.requestMatchers(HttpMethod.GET, "/api/blog/posts/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/blog/users/**").permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.httpBasic()
		;

		return http.build();
	}
}
