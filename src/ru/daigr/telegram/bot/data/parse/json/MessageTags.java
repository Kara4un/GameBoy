package ru.daigr.telegram.bot.data.parse.json;

public enum MessageTags {
	
	MESSAGE_ID("message_id"),
	FROM("from"),
	DATE("date"),
	CHAT("chat"),
	FORWARD_FROM("forward_from"),
	FORWARD_DATE("forward_date"),
	REPLY_TO_MESSAGE("reply_to_message"),
	TEXT("text"),
	AUDIO("audio"),
	DOCUMENT("document"),
	PHOTO("photo"),
	STICKER("sticker"),
	VIDEO("video"),
	CONTACT("contact"),
	LOCATION("location"),
	NEW_CHAT_PARTICIPANT("new_chat_participant"),
	LEFT_CHAT_PARTICIPANT("left_chat_participant"),
	NEW_CHAT_TITLE("new_chat_title"),
	NEW_CHAT_PHOTO("new_chat_photo"),
	DELETE_CHAT_PHOTO("delete_chat_photo"),
	GROUP_CHAT_CREATED("group_chat_created");
	
	private String name; 
	
	private MessageTags(String aName){
		name = aName;
	}
	
	public String toString() {
		return name;
	}
}
