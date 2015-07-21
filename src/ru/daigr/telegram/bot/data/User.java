package ru.daigr.telegram.bot.data;

public class User {
	
	public long id = 0;
	public String firstName = "";
	public String lastName = "";
	public String userName = "";
	
	public User (long anId){
		this.id = anId;
	}
	
	public void setId(long anId){
		this.id = anId;
	}
	
	public void setFirstName (String fN){
		firstName = fN;
	}
	
	public void setLastName (String lN){
		lastName = lN;
	}
	
	public void setUserName (String uN){
		userName = uN;
	}
	
	public long getId(){
		return id;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getUserName() {
		return userName;
	}

}
