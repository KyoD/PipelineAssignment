package edu.tus.movie.exception;

public class MovieValidationException extends MovieException {

	private static final long serialVersionUID = 334051992916748022L;

	public MovieValidationException(final String errorMessage) {
		super(errorMessage);
	}

}

