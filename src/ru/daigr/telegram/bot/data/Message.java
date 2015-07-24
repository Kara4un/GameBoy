package ru.daigr.telegram.bot.data;

public class Message {
	
	private long message_id;
	

	private User from = null;
	private long date = 0;
	private boolean isChat = false;
	
	//Если сообщение пришло из чата, то chatUser == null и наоборот 
	private User chatUser = null;	
	private GroupChat chat = null;

	private User forwardFrom = null;
	private long forwardDate = 0;
	private Message replayToMessage = null;
	private String text = "";
	
	private User newChatParticipant = null;
	private User leftChatParticipant = null;
	private PhotoSize[] photoSize = null;

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
	
	public long getMessage_id() {
		return message_id;
	}	
	
	public void setMessage_id(long message_id) {
		this.message_id = message_id;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public boolean isChat() {
		return isChat;
	}

	public void setChat(boolean isChat) {
		this.isChat = isChat;
	}

	public User getChatUser() {
		return chatUser;
	}

	public void setChatUser(User chatUser) {
		this.chatUser = chatUser;
	}

	public GroupChat getChat() {
		return chat;
	}

	public void setChat(GroupChat chat) {
		this.chat = chat;
	}

	public User getForwardFrom() {
		return forwardFrom;
	}

	public void setForwardFrom(User forwardFrom) {
		this.forwardFrom = forwardFrom;
	}

	public long getForwardDate() {
		return forwardDate;
	}

	public void setForwardDate(long forwardDate) {
		this.forwardDate = forwardDate;
	}

	public Message getReplayToMessage() {
		return replayToMessage;
	}

	public void setReplayToMessage(Message replayToMessage) {
		this.replayToMessage = replayToMessage;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getNewChatParticipant() {
		return newChatParticipant;
	}

	public void setNewChatParticipant(User newChatParticipant) {
		this.newChatParticipant = newChatParticipant;
	}
	
	public User getLeftChatParticipant() {
		return leftChatParticipant;
	}

	public void setLeftChatParticipant(User leftChatParticipant) {
		this.leftChatParticipant = leftChatParticipant;
	}

	public PhotoSize[] getPhotoSize() {
		return photoSize;
	}

	public void setPhotoSize(PhotoSize[] photoSize) {
		this.photoSize = photoSize;
	}
}
