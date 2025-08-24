package com.newsgg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class NewsAggregatorApplication {

	public static void main(String[] args) {

        SpringApplication.run(NewsAggregatorApplication.class, args);
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		try {
			 http
	            .csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/api/register", "/api/login", "/h2-console/**","/newsapi/**").permitAll()
	                
	                .anyRequest().authenticated()
	            )

	            .addFilterBefore(new JWTAuthenticationFilter(),
	            					UsernamePasswordAuthenticationFilter.class);


	        return http.build();
		}catch(Exception ex) {
            ex.printStackTrace(); // or use a logger
            throw new RuntimeException("Failed to build SecurityFilterChain", ex);

        }

    }

    @Bean
    public WebClient webClient(){

        return WebClient.builder()
                .baseUrl("https://newsapi.org")
                .build();
    }


}
