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
	
	public static GroupChat defaultGroupChat(){
		return new GroupChat(0);
	}
	
	public boolean equals(GroupChat chat) {
		return chat.getId() == id && title.equals(chat.getTitle()); 
	}
}
