package edu.tus.movie.errors;

public enum ErrorMessages {
	EMPTY_FIELDS("One of more empty field"),
	ALREADY_EXISTS("This movie already exists"),
	NO_NAME_UPDATE("Movie name cannot be updated!"),
	NO_MOVIE_FOUND("No movie found");
	
	private String errorMessage;
	
	ErrorMessages(String errMsg){
		this.errorMessage=errMsg;
	}
	
	public String getMsg(){
		return errorMessage;
	}
}
