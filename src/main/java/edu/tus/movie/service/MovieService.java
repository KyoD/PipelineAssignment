package edu.tus.movie.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tus.movie.errors.ErrorMessages;
import edu.tus.movie.errors.ErrorValidation;
import edu.tus.movie.exception.MovieValidationException;
import edu.tus.movie.model.Movie;
import edu.tus.movie.repository.MovieRepository;

@Service
public class MovieService {

	MovieRepository movieRepository;

	ErrorValidation errorValidation;
	
	@Autowired
	public void setMovieRepository(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}
	
	@Autowired
	public void setErrorVlidation(ErrorValidation errorValidation) {
		this.errorValidation=errorValidation;
	}

	// CREATE
	public Movie createMovie(Movie movie) throws MovieValidationException {
		checkForEmptyFields(movie);
		checkForInvalidYear(movie);
		if (movieRepository.findByName(movie.getName()) != null) {
			throw new MovieValidationException(ErrorMessages.ALREADY_EXISTS.getMsg());
		}
		return movieRepository.save(movie);
	}

	
	public List<Movie> findAllMovies() {
		return movieRepository.findAll();
	}

	
	public Movie updateMovie(Long id, Movie movie) throws MovieValidationException {
		Optional<Movie> optionalStoredMovie = movieRepository.findById(id);
		if (optionalStoredMovie.isEmpty()) {
			throw new MovieValidationException(ErrorMessages.NO_MOVIE_FOUND.getMsg());
		}
		Movie storedMovie = optionalStoredMovie.get();
		
		checkForEmptyFields(movie);
		checkForInvalidYear(movie);
		if (!(movie.getName()).equals(storedMovie.getName())){
			throw new MovieValidationException(ErrorMessages.NO_NAME_UPDATE.getMsg());
		}
		return movieRepository.save(movie);
	}

	public void deleteMovie(Long id) throws MovieValidationException {
		Optional<Movie> storedMovie = movieRepository.findById(id);
		if (storedMovie.isEmpty()) {
			throw new MovieValidationException(ErrorMessages.NO_MOVIE_FOUND.getMsg());
		}
		movieRepository.delete(storedMovie.get());
		
	}
	
	private void checkForEmptyFields(Movie movie) throws MovieValidationException {	
		if (errorValidation.emptyOrNullFields(movie)) {
			throw new MovieValidationException(ErrorMessages.EMPTY_FIELDS.getMsg());
		}
	}
	
	private void checkForInvalidYear(Movie movie) throws MovieValidationException {	
		if (errorValidation.isYearInvalid(movie)) {
			throw new MovieValidationException(ErrorMessages.INVALID_YEAR.getMsg());
		}
	}
}

