package edu.tus.movie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.tus.movie.errors.ErrorMessage;
import edu.tus.movie.exception.MovieException;
import edu.tus.movie.model.Movie;
import edu.tus.movie.service.MovieService;


@RestController
@RequestMapping("/api/movies")
public class MovieController {

	
	MovieService movieService;
	
	@Autowired
	public void setMovieService(MovieService movieService) {
		this.movieService = movieService;
	}
	
	@GetMapping
	public List<Movie> getMovies(){
		return movieService.findAllMovies();
	}

	@PostMapping
	public ResponseEntity addMovie(@RequestBody Movie movie) {
		try {
			Movie savedEmployee = movieService.createMovie(movie);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
		} catch (MovieException e) {
			ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(errorMessage);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity updateMovie(@PathVariable("id") Long id, @RequestBody Movie movie) {
		try {
			Movie updatedEmployee = movieService.updateMovie(id, movie);
			return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
		} catch (MovieException e) {
			ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(errorMessage);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteMovie(@PathVariable("id") Long id) {
		try {
			movieService.deleteMovie(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception e) {
			ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}
	}

}



