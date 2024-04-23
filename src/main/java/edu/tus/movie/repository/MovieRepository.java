package edu.tus.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.tus.movie.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	List<Movie> findByDirector(String director);
	Movie findByName(String name);
}