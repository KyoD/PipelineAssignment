package edu.tus.movie.errors;
import org.springframework.stereotype.Component;

import edu.tus.movie.model.Movie;

@Component
public class ErrorValidation {
	
//	@Autowired
//    EmployeeRepository empRepository;
	
	public boolean emptyFields(Movie movie) {
		return ((movie.getDirector().length()==0||movie.getName().length()==0
				||movie.getYear().length()==0));
	}
}
