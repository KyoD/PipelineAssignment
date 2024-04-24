package error;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.tus.movie.errors.ErrorValidation;
import edu.tus.movie.model.Movie;


public class ErrorValidationTest {
	ErrorValidation errorValidation;
	Movie movie;
	
	@BeforeEach
	void setUp() {
		errorValidation = new ErrorValidation();
		movie = new Movie();
		movie.setDirector("Christopher Nolan");
		movie.setName("Interstellar");
		movie.setYear("2014");
	}
	
	@Test
	void validName() {
		movie.setName("Interstellar");
		assertFalse(errorValidation.emptyOrNullFields(movie));
	}
	
	@Test
	void invalidName() {
		movie.setName("");
		assertTrue(errorValidation.emptyOrNullFields(movie));
		
		movie.setName(null);
		assertTrue(errorValidation.emptyOrNullFields(movie));
	}
	
	@Test
	void validDirector() {
		movie.setDirector("Steven Spielberg");
		assertFalse(errorValidation.emptyOrNullFields(movie));
	}
	
	@Test
	void invalidDirector() {
		movie.setDirector("");
		assertTrue(errorValidation.emptyOrNullFields(movie));
		
		movie.setDirector(null);
		assertTrue(errorValidation.emptyOrNullFields(movie));
	}
	
	@Test
	void validYear() {
		movie.setYear("2020");
		assertFalse(errorValidation.emptyOrNullFields(movie));
	}
	
	@Test
	void invalidYear() {
		movie.setYear("");
		assertTrue(errorValidation.emptyOrNullFields(movie));
		
		movie.setYear(null);
		assertTrue(errorValidation.emptyOrNullFields(movie));
	}
}