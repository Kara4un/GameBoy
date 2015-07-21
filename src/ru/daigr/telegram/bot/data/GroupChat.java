package ru.daigr.telegram.bot.data;

public class GroupChat {
	
	private long id = 0;
	private String title = "";
	
	public GroupChat(long anId){
		id = anId;
	}
	
	public void setTitle (String aTitle){
		title = aTitle;
	}

	public long getId(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
}
