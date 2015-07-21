package ru.daigr.telegram.bot.data;

public class Message {
	
	public long message_id;
	public User from = null;
	public long date = 0;
	public boolean isChat = false;
	
	//Если сообщение пришло из чата, то chatUser == null и наоборот 
	public User chatUser = null;	
	public GroupChat chat = null;

	public User forwardFrom = null;
	public long forwardDate = 0;
	public Message replayToMessage = null;
	public String text = "";
	
	public User newChatParticipant = null;
	
	public Message(long anId){
		message_id = anId;
	}
	
	/*Unparseble data
	TODO
	
	audio
	document
	photo
	sticker
	video
	contact
	location
	
	newChatTitle
	newChatPhoto
	groupChatCreated
	deleteChatPhoto
	*/
	
	public static Message defaultMessage(){
		return new Message(0);
	}
}
