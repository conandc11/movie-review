package com.example.manytomany.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.manytomany.entity.Movie;
import com.example.manytomany.entity.Rating;
import com.example.manytomany.entity.RatingKey;
import com.example.manytomany.entity.User;
import com.example.manytomany.repository.MovieRepository;
import com.example.manytomany.repository.RatingRepository;
import com.example.manytomany.repository.UserRepository;
import com.example.manytomany.request.entity.ReviewObject;

import javassist.NotFoundException;

@Service
public class RatingService extends AbstractService<Rating> {

	private RatingRepository ratingRepository;
	private MovieRepository movieRepository;
	private UserRepository userRepository;
	private UserService userService;

	public RatingService(RatingRepository ratingRepository, MovieRepository movieRepository,
			com.example.manytomany.repository.UserRepository userRepository, UserService userService) {
		this.ratingRepository = ratingRepository;
		this.movieRepository = movieRepository;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	public HashMap<String, Integer> getTopCriticMovies(String genreName, int count) {
		List<Rating> ratingList = ratingRepository.getTopCriticMoviesByGenre(genreName, count);

		HashMap<String, Integer> movieScoreMap = new HashMap<>();
		for (Rating rating : ratingList) {
			String movieName = rating.getMovie().getMovieName();
			if (movieScoreMap.containsKey(movieName)) {
				movieScoreMap.put(movieName, movieScoreMap.get(movieName) + rating.getScore());
			} else {
				movieScoreMap.put(movieName, rating.getScore());
			}
		}
		return movieScoreMap;
	}

	public double getAvgMovieScoreByYear(String date) {
		Date date1 = convertToDate(date + "-01-01");
		Date date2 = convertToDate(date + "-12-31");
		System.out.println("date1 " + date1 + "date2 " + date2);
		double avgMovieScore = 0.00;
		try {
			avgMovieScore = ratingRepository.getAvgMovieScoreByYear(date1, date2);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("no movie reviews found for this year");
		}
		return avgMovieScore;
	}

	// add a review
	public String addReview(ReviewObject reviewRequest) throws Exception {
		Rating newRating = new Rating();
		Movie newMovie = new Movie();
		User newUser = new User();

		// if user and movie_name both exists then we add a review
		if (userRepository.getByName(reviewRequest.getUsername()) != null
				&& movieRepository.existsByName(reviewRequest.getMovieName()) != null) {

			newUser = userRepository.getByName(reviewRequest.getUsername());
			newMovie = movieRepository.existsByName(reviewRequest.getMovieName());

			String movieName = reviewRequest.getMovieName();
			String releaseDate = movieRepository.existsByName(movieName).getReleaseDate();
			String username = reviewRequest.getUsername();
			java.sql.Date reviewDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			String currentUserRole = newUser.getCurrentRole();
			System.out.println("currentUserRole " + currentUserRole);
			int userScore = reviewRequest.getRatingScore();
			
			// calc weighted review score based on role
			int weightedScore = (currentUserRole.equals("Viewer")) ? userScore : 2 * userScore;

			try {
				if (isMovieReleased(releaseDate) && ratingRepository.ratingExists(movieName, username) == null) {
					newRating.setDateOfReview(reviewDate);

					System.out.println(newMovie.toString());

					System.out.println(newUser.toString());

					// modify rating based on role
					newRating.setScore(weightedScore);
					newRating.setMovie(newMovie);
					// add the role to rating
					newRating.setRole(currentUserRole);
					RatingKey ratingKey = new RatingKey();
					newRating.setId(ratingKey);
					newRating.setUser(newUser);
					System.out.println(newRating.toString());

					ratingRepository.save(newRating);

				} else if (isMovieReleased(releaseDate) == false) {
					System.out.println(" Exception : movie yet to be released ");

					throw new NotFoundException("Exception : movie is yet to be released");
				} else {
					System.out.println("Exception :  multiple reviews not allowed on same movie by one user");

					throw new NotFoundException("Exception : multiple reviews not allowed");

				}
			} catch (Exception e) {
				System.out.println("exception occurred on line 88 in  RatingService.java");
			}

		} else {
			throw new NotFoundException("either movie or user doesn't exists");
		}
		// updates user's role when a new review is added
		userService.updateUserRole(reviewRequest.getUsername());

		return "success";
	}

	static int viewerRating = 0;

	// Average review score for a particular movie
	public int getAvgMovieRating(String movieName) {
		int avgMovieScore = ratingRepository.getReviewedMoviesByName(movieName);

		return avgMovieScore;

	}

	// convert String to date
	public Date convertToDate(String date) {
		Date outputDate = Date.valueOf(date);
		return outputDate;
	}

	// check if movie is released in current year
	public boolean isMovieReleased(String yearString) {
		java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		Date releaseDate = convertToDate(yearString + "-12-31");
		if (currentDate.compareTo(releaseDate) < 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected JpaRepository<Rating, Long> getJPADao() {
		// TODO Auto-generated method stub
		return ratingRepository;
	}

}
