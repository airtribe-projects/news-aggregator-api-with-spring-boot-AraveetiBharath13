package com.newsgg.entity;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

@Entity
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
    private String userName;
    private String userPassword;
    private List<String> newsPreferences;
    private List<String> favourites;
    private String phoneNumber;
    @Convert(converter = CachedNewsConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<NewsAPI.Article> cachedNews;


    public List<NewsAPI.Article> getCachedNews() {
        return cachedNews;
    }

    public void setCachedNews(List<NewsAPI.Article> cachedNews) {
        this.cachedNews = cachedNews;
    }

    public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public List<String> getNewsPreferences() {
		return newsPreferences;
	}
	public void setNewsPreferences(List<String> newsPreferences) {
		this.newsPreferences = newsPreferences;
	}
	public List<String> getFavourites() {
		return favourites;
	}
	public void setFavourites(List<String> favourites) {
		this.favourites = favourites;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	

}
