package com.newsgg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.newsgg.TokenGeneratorUtility;
import com.newsgg.entity.UserDTO;
import com.newsgg.entity.UserEntity;
import com.newsgg.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	public UserEntity registerUser(UserDTO userEntity) {
		if(userRepository.getByUserName(userEntity.getUserName()) == null) {
			return null;
		}
		
		String hashedPassword = passwordEncoder
				.encode(userEntity.getPassword());
		
		UserEntity user = new UserEntity();
		user.setUserName(userEntity.getUserName());
		user.setUserPassword(hashedPassword);
		
		return userRepository.save(user);
	}
	
	public String loginUser(String username,String password){
			
			UserEntity userFetched = userRepository.getByUserName(username);
			
			if(userFetched == null) {
				return "User not Found";
			}
			
			if(passwordEncoder.matches(password, userFetched.getUserPassword())) {
				return TokenGeneratorUtility.generateToke(username, "user");
			}
			return "Invalid User";
		}

	

	
}
