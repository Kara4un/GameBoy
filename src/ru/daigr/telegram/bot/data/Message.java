package ru.daigr.telegram.bot.data;

public class Message {
	
	public long message_id;
	public User from;
	public long date;
	public boolean isChat;
	
	//Если сообщение пришло из чата, то chatUser == null и наоборот 
	public User chatUser;	
	public GroupChat chat;

	public User forwardFrom;
	public long forwardDate;
	public Message replayToMessage;
	public String text;
	
	public User newChatParticipant;
	
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
}
