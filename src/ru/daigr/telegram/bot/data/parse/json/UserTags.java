package ru.daigr.telegram.bot.data.parse.json;

public enum UserTags {
	
	ID("id"),
	FIRST_NAME("first_name"),
	LAST_NAME("last_name"),
	USERNAME("username");
	
	private String name; 
	
	private UserTags(String aName){
		name = aName;
	}
	
	public String toString() {
		return name;
	}

}
