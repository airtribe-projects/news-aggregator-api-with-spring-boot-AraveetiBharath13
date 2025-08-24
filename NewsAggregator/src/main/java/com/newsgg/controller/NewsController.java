package com.newsgg.controller;

import com.newsgg.entity.NewsAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.newsgg.entity.UserDTO;
import com.newsgg.entity.UserEntity;
import com.newsgg.service.UserService;

import java.util.List;

@RestController
public class NewsController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/api/register")
	public String userRegistration(@RequestBody UserDTO user) {

		if(userService.registerUser(user) != null) {
			return "User Register Sucessfully proceed to login";
		}
		
		return "User Registration failed or User already exists";
	}
	
	@PostMapping("/api/login")
	public String userLogin(@RequestParam String username, 
			@RequestParam String password) {
		
		return userService.loginUser(username, password);
	}

    @GetMapping("/api/news")
    public List<NewsAPI.Article> getFavourites(@RequestParam String userName) {
         return userService.getCachedNews(userName);
    }

//    GET /api/preferences: Retrieve the news preferences for the logged-in user.
//
//    PUT /api/preferences

    @GetMapping("/api/preferences")
    public List<String> getPrefernces(@RequestParam String userName) {
        return userService.getUserPreferences(userName);
    }

    @PutMapping("/api/preferences")
    public List<String> updatePrefernces(@RequestParam String userName,
                                         @RequestParam List<String> prefernces){
        return userService.updateUserPrefernces(prefernces,userName);
    }

}
