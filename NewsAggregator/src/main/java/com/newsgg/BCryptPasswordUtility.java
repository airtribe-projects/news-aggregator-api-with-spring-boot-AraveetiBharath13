package com.newsgg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BCryptPasswordUtility {
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		
		return new BCryptPasswordEncoder(11);
		
	}
	
	

}
