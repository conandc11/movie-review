package com.example.manytomany.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.manytomany.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
	@Query(value = "select * from rating where role='Critic' and movie_id in (select id from movie where id in (select movie_id from movie_genre where  genre_genre_id in (select genre_id from genre where genre_name=?1))) group by movie_id order by user_id desc limit ?2", nativeQuery = true)
	List<Rating> getTopCriticMoviesByGenre(String genreName, int count);
	
	@Query(value = "Select round(avg(score),2) from rating where date_of_review between ?1 and ?2 ", nativeQuery = true)
	double getAvgMovieScoreByYear(Date date1, Date date2);

	@Query(value = "Select round(avg(score),2) from rating where movie_id in (select id from movie where movie_name=?1)", nativeQuery = true)
	int getReviewedMoviesByName(String movieName);

	@Query(value = "select * from rating where movie_id in (select id from movie where movie_name=?1) and user_id=(select id from user where username=?2) limit 1", nativeQuery = true)
	Rating ratingExists(String movieName, String username);

	@Query(value = "Select count(*) from rating where user_id=?1", nativeQuery = true)
	int getRatingByUserId(long userId);

}
