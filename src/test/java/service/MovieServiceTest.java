package service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.tus.movie.errors.ErrorMessages;
import edu.tus.movie.errors.ErrorValidation;
import edu.tus.movie.exception.MovieValidationException;
import edu.tus.movie.model.Movie;
import edu.tus.movie.repository.MovieRepository;
import edu.tus.movie.service.MovieService;


public class MovieServiceTest {
	MovieRepository movieRepository;
	MovieService movieService;
	
	@BeforeEach
	void setUp() {
		movieRepository = mock(edu.tus.movie.repository.MovieRepository.class);
		movieService = new MovieService();
		movieService.setMovieRepository(movieRepository);
		movieService.setErrorVlidation(new ErrorValidation());
	}
	
	Movie buildValidMovie() {
		Movie movie = new Movie();
		movie.setYear("2014");
		movie.setDirector("Christopher Nolan");
		movie.setName("Interstellar");
		return movie;
	}
	
	@Test
	void createEmployeeSuccess() throws MovieValidationException {
		Movie movie = buildValidMovie();
		Movie newMovie = buildValidMovie();
		newMovie.setId(10L);
		
		when(movieRepository.findByName(movie.getName())).thenReturn(null);
		when(movieRepository.save(movie)).thenReturn(newMovie);
		
		Movie addedEmployee = movieService.createMovie(movie);
		assertNotNull(addedEmployee);
		assertEquals(addedEmployee.getId(), 10L);
	}
	
	@Test
	void createEmployeeExistingFail() throws MovieValidationException {
		Movie movie = buildValidMovie();
		Movie existingMovie = buildValidMovie();
		existingMovie.setId(10L);
		
		when(movieRepository.findByName(movie.getName())).thenReturn(existingMovie);
		
		MovieValidationException ex = assertThrows(MovieValidationException.class, () -> movieService.createMovie(movie));
		assertEquals(ex.getMessage(), ErrorMessages.ALREADY_EXISTS.getMsg());
	}
	
	@Test
	void createEmployeeEmptyFieldFail() throws MovieValidationException {
		Movie movie = buildValidMovie();
		movie.setName("");
		
		when(movieRepository.findByName(movie.getName())).thenReturn(null);

		MovieValidationException ex = assertThrows(MovieValidationException.class, () -> movieService.createMovie(movie));
		assertEquals(ex.getMessage(), ErrorMessages.EMPTY_FIELDS.getMsg());
	}

	
	@Test
	void updateMovieSuccess() throws MovieValidationException {
		long id = 10L;
		Movie movie = buildValidMovie();
		
		when(movieRepository.findById(id)).thenReturn(Optional.ofNullable(movie));
		when(movieRepository.save(movie)).thenReturn(movie);
		
		Movie updatedEmployee = movieService.updateMovie(id, movie);
		assertNotNull(updatedEmployee);
	}
	
	@Test
	void updateMovieNotExistsFail() throws MovieValidationException {
		long id = 10L;
		Movie movie = buildValidMovie();
		
		when(movieRepository.findById(id)).thenReturn(Optional.ofNullable(null));
		
		MovieValidationException ex = assertThrows(MovieValidationException.class, () -> movieService.updateMovie(id, movie));
		assertEquals(ex.getMessage(), ErrorMessages.NO_MOVIE_FOUND.getMsg());
	}
	
	@Test
	void updateMovieEmptyFieldFail() throws MovieValidationException {
		long id = 10L;
		Movie movie = buildValidMovie();
		movie.setYear("");
		
		when(movieRepository.findById(id)).thenReturn(Optional.ofNullable(movie));
		
		MovieValidationException ex = assertThrows(MovieValidationException.class, () -> movieService.updateMovie(id, movie));
		assertEquals(ex.getMessage(), ErrorMessages.EMPTY_FIELDS.getMsg());
	}
	
	@Test
	void updateMovieNoNameChangeFail() throws MovieValidationException {
		long id = 10L;
		Movie movie = buildValidMovie();
		movie.setName("NEW NAME");
		Movie existingEmployee = buildValidMovie();
		
		when(movieRepository.findById(id)).thenReturn(Optional.ofNullable(existingEmployee));
		
		MovieValidationException ex = assertThrows(MovieValidationException.class, () -> movieService.updateMovie(id, movie));
		assertEquals(ex.getMessage(), ErrorMessages.NO_NAME_UPDATE.getMsg());
	}
	
	@Test
	void deleteMovieSuccess() throws MovieValidationException {
		long id = 10L;
		Movie employee = buildValidMovie();
		
		when(movieRepository.findById(id)).thenReturn(Optional.ofNullable(employee));
		assertDoesNotThrow(() -> movieService.deleteMovie(id));
	}
	
	@Test
	void deleteMovieFail() throws MovieValidationException {
		long id = 10L;
		
		when(movieRepository.findById(id)).thenReturn(Optional.ofNullable(null));
		MovieValidationException ex = assertThrows(MovieValidationException.class, () -> movieService.deleteMovie(id));
		assertEquals(ex.getMessage(), ErrorMessages.NO_MOVIE_FOUND.getMsg());
	}
	
	@Test
	void getAllMovies() {
		List<Movie> response = movieService.findAllMovies();
		assertNotNull(response);
	}
}
