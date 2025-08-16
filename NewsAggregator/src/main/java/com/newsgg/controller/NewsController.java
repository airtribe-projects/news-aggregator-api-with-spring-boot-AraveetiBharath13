package com.newsgg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsgg.entity.UserDTO;
import com.newsgg.entity.UserEntity;
import com.newsgg.service.UserService;

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

}
