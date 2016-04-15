package com.hhiregistry.exceptions;

public class MemberExistsException extends Exception {
	
	private String description;
	
	
	public MemberExistsException(){
		
		
	}
	
	public MemberExistsException(String description){
		this.description = description;
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
