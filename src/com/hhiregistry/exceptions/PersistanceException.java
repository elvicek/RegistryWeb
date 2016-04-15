package com.hhiregistry.exceptions;

public class PersistanceException extends Exception{
	
	private String description;

	
	public PersistanceException(){
		this("Persistance Exception");
	}
	
   public PersistanceException(String description){
	   this.description = description;
		
	}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}
}
