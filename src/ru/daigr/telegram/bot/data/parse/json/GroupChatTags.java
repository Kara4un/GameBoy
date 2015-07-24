package ru.daigr.telegram.bot.data.parse.json;

public enum GroupChatTags {
	
	ID("id"),
	TITLE("title");	
	
	private String name; 
	
	private GroupChatTags(String aName){
		name = aName;
	}
	
	public String toString() {
		return name;
	}

}
