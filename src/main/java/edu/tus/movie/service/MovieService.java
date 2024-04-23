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
		if (movieRepository.findByName(movie.getName()) != null) {
			throw new MovieValidationException(ErrorMessages.ALREADY_EXISTS.getMsg());
		}
		return movieRepository.save(movie);
	}

	
	public List<Movie> findAllMovies() {
		return movieRepository.findAll();
	}

	
	public Movie updateMovie(Long id, Movie movie) throws MovieValidationException {
		Optional<Movie> storedEmployee = movieRepository.findById(id);
		if (storedEmployee.isEmpty()) {
			throw new MovieValidationException(ErrorMessages.NO_MOVIE_FOUND.getMsg());
		}
		checkForEmptyFields(movie);
		if (!(movie.getName()).equals(storedEmployee.get().getName())){
			throw new MovieValidationException(ErrorMessages.NO_NAME_UPDATE.getMsg());
		}
		return movieRepository.save(movie);
	}

	public void deleteMovie(Long id) throws MovieValidationException {
		Optional<Movie> storedEmployee=movieRepository.findById(id);
		if (storedEmployee.isEmpty()) {
			throw new MovieValidationException(ErrorMessages.NO_MOVIE_FOUND.getMsg());
		}
		movieRepository.delete(storedEmployee.get());
		
	}
	
	private void checkForEmptyFields(Movie movie) throws MovieValidationException {	
		if (errorValidation.emptyFields(movie)) {
			throw new MovieValidationException(ErrorMessages.EMPTY_FIELDS.getMsg());
		}
	}
}

