package com.example.manytomany.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.manytomany.entity.Movie;
import com.example.manytomany.repository.MovieRepository;

@Service
public class MovieService extends AbstractService<Movie> {

	private MovieRepository movieRepository;

	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	public String addMovie(Movie movie) {

		if (movieRepository.existsByName(movie.getMovieName()) != null) {
			return "movie already exists";
		} else {
			Movie newMovie = new Movie();
			newMovie.setMovieName(movie.getMovieName());
			newMovie.setReleaseDate(movie.getReleaseDate());
			newMovie.setGenre(movie.getGenre());
			movieRepository.save(newMovie);
			return "success";
		}
	}

	@Override
	protected JpaRepository<Movie, Long> getJPADao() {
		// TODO Auto-generated method stub
		return movieRepository;
	}

}
