package com.aes.exceptions;

public class UsersExistInRoleException extends Exception {
	
	private String description;
	
	
	public UsersExistInRoleException(){
		
		
	}
	
	public UsersExistInRoleException(String description){
		this.description = description;
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
