package com.example.manytomany.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.manytomany.entity.Movie;
import com.example.manytomany.entity.User;
import com.example.manytomany.request.entity.ReviewObject;
import com.example.manytomany.service.MovieService;
import com.example.manytomany.service.RatingService;
import com.example.manytomany.service.UserService;

@RestController
@RequestMapping("demo")
public class RatingController {

	private MovieService movieService;

	private RatingService ratingService;

	private UserService userService;

	public RatingController(MovieService movieService, RatingService ratingService, UserService userService) {
		this.movieService = movieService;
		this.ratingService = ratingService;
		this.userService = userService;
	}

	@PostMapping("/movie/add")
	public String addMovie(@RequestBody Movie movie) {
		return movieService.addMovie(movie);
	}

	@PostMapping("/user/add")
	public String addUser(@RequestBody User user) {
		return userService.addUser(user);
	}

	@PostMapping("/rating/add")
	public String addRating(@RequestBody ReviewObject reviewObject) {
		try {
			return ratingService.addReview(reviewObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.fillInStackTrace();
		}
		return "success";
	}

	// 3. List top n movies by total Rating score by ‘critics’ in a particular
	// genre.
	@GetMapping("/top/movies/{genreName}/{count}")
	public HashMap<String, Integer> topCriticMovies(@PathVariable String genreName, @PathVariable int count) {
		return ratingService.getTopCriticMovies(genreName, count);
	}

	// 4. Average Rating score in a particular year of release.
	@GetMapping("/movies/rating/{year}")
	public double avgMovieRatingYearWise(@PathVariable String year) {
		return ratingService.getAvgMovieScoreByYear(year);
	}

	// 5. Average Rating score for a particular movie.
	@GetMapping("/movies/avg/{name}")
	public int avgMovieRating(@PathVariable String name) {
		return ratingService.getAvgMovieRating(name);
	}
}
