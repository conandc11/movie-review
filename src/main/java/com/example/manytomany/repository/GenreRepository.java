package com.example.manytomany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.manytomany.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
