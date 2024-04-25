package edu.tus.movie.errors;
import org.springframework.stereotype.Component;

import edu.tus.movie.model.Movie;

@Component
public class ErrorValidation {
	
	public boolean emptyOrNullFields(Movie movie) {
		if(movie.getDirector() == null || movie.getName() == null
				|| movie.getYear() == null) {
			return true;
		}
		
		return (movie.getDirector().length() == 0 || movie.getName().length() == 0
				|| movie.getYear().length() == 0);
	}
	
	public boolean isYearInvalid(Movie movie) {
		String year = movie.getYear();
		if(year == null || year.length() == 0)
			return true;
		
		Integer yearInt = Integer.parseInt(year);
		return yearInt < 1975 || yearInt > 2024;
	}
}
