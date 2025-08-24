package com.newsgg.service;

import com.newsgg.entity.NewsAPI;
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
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

    @Autowired
    private WebClient _webClient;

    public List<String> getUserPreferences(String userName){
        return userRepository.getByUserName(userName).getNewsPreferences();
    }
    public List<String> updateUserPrefernces(List<String> prefernces, String userName){
         UserEntity fetchedUser = userRepository.getByUserName(userName);
         fetchedUser.getNewsPreferences().addAll(prefernces);
         getNews(fetchedUser);
         return fetchedUser.getNewsPreferences();
    }

	public UserEntity registerUser(UserDTO userEntity) {
		if(userRepository.getByUserName(userEntity.getUserName()) != null) {
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
                getNews(userFetched);
				return TokenGeneratorUtility.generateToke(username, "USER");
			}
			return "Invalid User";
		}

        public List<NewsAPI.Article> getNews(UserEntity user ){


            if(user.getNewsPreferences() == null){
                List<String> preferences = new ArrayList<>();
                preferences.add("apple");
                user.setNewsPreferences(preferences);
            }
            List<NewsAPI.Article> allArticles = new ArrayList<>();

            for (String s : user.getNewsPreferences()) {
                List<NewsAPI.Article> articles = _webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/v2/everything")
                                .queryParam("q", s)
                                .queryParam("apiKey", "e0d1877ee8a74223a8db130713c90068")
                                .build())
                        .retrieve()
                        .bodyToMono(NewsAPI.class)
                        .map(NewsAPI::getArticles)
                        .block();

                if (articles != null) {
                    allArticles.addAll(articles);
                }
            }

            user.setCachedNews(allArticles);
            userRepository.save(user);

            return allArticles;
        }


        public List<NewsAPI.Article> getCachedNews(String username){
            UserEntity userFetched = userRepository.getByUserName(username);
            if(userFetched != null && userFetched.getCachedNews() != null) {
               return getNews(userFetched);
            }
           return userFetched.getCachedNews();
        }
	
}
