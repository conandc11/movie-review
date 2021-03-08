package com.example.manytomany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.manytomany.entity.Genre;
import com.example.manytomany.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query(value = "select * from movie where movie_name=?1", nativeQuery = true)
	Movie existsByName(String movieName);
	
	@Query(value = "select * from movie where movie_id=?1", nativeQuery = true)
	Movie getMovieById(long l);

	@Query(value = "select * from genre where genre_id = (select genre_genre_id from movie_genre where genre_genre_id=?1)", nativeQuery = true)
	Genre genreByName(int genreId);
}