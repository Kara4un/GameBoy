package ru.daigr.telegram.bot.data.parse.json;

public enum UpdateTags {
	UPDATE_ID("update_id"),
	MESSAGE("message");
	
	private String name; 
	
	private UpdateTags(String aName){
		name = aName;
	}
	
	public String toString() {
		return name;
	}
}
