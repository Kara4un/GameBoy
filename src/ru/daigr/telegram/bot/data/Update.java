package ru.daigr.telegram.bot.data;

public class Update {
	
	private long id;
	private Message message;	
		
	public Update(long anId, Message aMessage){
		id = anId;
		message = aMessage;		
	}
	
	public long getId(){
		return id;
	}
	
	public Message getMessage(){
		return message;
	}
	
	public static Update defultUpdate(){
		return new Update(0, Message.defaultMessage());
	}

}
