package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.tus.movie.controller.MovieController;
import edu.tus.movie.errors.ErrorMessage;
import edu.tus.movie.errors.ErrorMessages;
import edu.tus.movie.exception.MovieValidationException;
import edu.tus.movie.model.Movie;
import edu.tus.movie.service.MovieService;

public class MovieControllerTest {
	MovieService movieService;
	MovieController movieController;
	
	@BeforeEach
	void setUp() {
		movieController = new MovieController();
		movieService = mock(edu.tus.movie.service.MovieService.class);
		movieController.setMovieService(movieService);
	}
	
	Movie buildValidMovie() {
		Movie movie = new Movie();
		movie.setName("Interstellar");
		movie.setDirector("Christopher Nolan");
		movie.setYear("2014");
		return movie;
	}
	
	@Test
	void addMovieSuccess() throws MovieValidationException {
		long newId = 100L;
		Movie movie = buildValidMovie();
		Movie newMovie = buildValidMovie();
		newMovie.setId(newId);
		
		when(movieService.createMovie(movie)).thenReturn(newMovie);
		ResponseEntity response = movieController.addMovie(movie);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		Movie createdMovie = (Movie)response.getBody();
		assertEquals(newId, createdMovie.getId());
	}
	
	@Test
	void addMovieFail() throws MovieValidationException {
		Movie movie = buildValidMovie();
		
		ErrorMessage errorTothrow = new ErrorMessage(ErrorMessages.EMPTY_FIELDS.getMsg());
		when(movieService.createMovie(movie)).thenThrow(new MovieValidationException(errorTothrow.getErrorMessage()));
		ResponseEntity response = movieController.addMovie(movie);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		ErrorMessage errorReturned = (ErrorMessage)response.getBody();
		assertEquals(errorTothrow.getErrorMessage(), errorReturned.getErrorMessage());
	}
	
	@Test
	void updateMovieSuccess() throws MovieValidationException {
		long id = 100L;
		Movie movie = buildValidMovie();
		movie.setId(id);
		movie.setYear("1997");
		
		when(movieService.updateMovie(id, movie)).thenReturn(movie);
		ResponseEntity response = movieController.updateMovie(id, movie);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		Movie updatedMovie = (Movie)response.getBody();
		assertEquals(updatedMovie.getYear(), updatedMovie.getYear());
	}
	
	@Test
	void updateMovieFail() throws MovieValidationException {
		long id = 100L;
		Movie movie = buildValidMovie();
		movie.setId(id);
		movie.setName("Insterstellar 3");
		
		ErrorMessage errorTothrow = new ErrorMessage(ErrorMessages.NO_NAME_UPDATE.getMsg());
		when(movieService.updateMovie(id, movie)).thenThrow(new MovieValidationException(errorTothrow.getErrorMessage()));
		ResponseEntity response = movieController.updateMovie(id, movie);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		ErrorMessage errorReturned = (ErrorMessage)response.getBody();
		assertEquals(errorTothrow.getErrorMessage(), errorReturned.getErrorMessage());
	}
	
	@Test
	void deleteMovieSuccess() throws MovieValidationException {
		long id = 100L;
		ResponseEntity response = movieController.deleteMovie(id);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void deleteMovieFail() throws MovieValidationException {
		long id = 100L;
		
		ErrorMessage errorTothrow = new ErrorMessage(ErrorMessages.NO_MOVIE_FOUND.getMsg());
		doThrow(new MovieValidationException(errorTothrow.getErrorMessage())).when(movieService).deleteMovie(id);

		ResponseEntity response = movieController.deleteMovie(id);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
		ErrorMessage errorReturned = (ErrorMessage)response.getBody();
		assertEquals(errorTothrow.getErrorMessage(), errorReturned.getErrorMessage());
	}
	
	@Test
	void getAllMovies() {
		List<Movie> response = movieController.getMovies();
		assertNotNull(response);
	}
}
