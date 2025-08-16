package com.newsgg.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int userId;
	public String userName;
	public String userPassword;
	public List<String> newsPreferences;
	public List<String> favourites;
	public String phoneNumber;
	
	
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
