package com.example.manytomany.request.entity;

import org.springframework.stereotype.Component;

@Component
public class ReviewObject {

	private String username;
	private String movieName;
	private int ratingScore;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public int getRatingScore() {
		return ratingScore;
	}

	public void setRatingScore(int ratingScore) {
		this.ratingScore = ratingScore;
	}

}
