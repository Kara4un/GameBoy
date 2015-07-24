package ru.daigr.telegram.bot;

public enum BotProperties {

	VOID(""),
	TELEGRAM_HOST("telegramHost"),
	TOKEN("token"),
	PERMANENT_CHAT_NAME("permanentChatName"),	
	PERMANENT_CHAT_ID("permanentChatId"),
	CHAT_TO_REPLY("chatToReply");
	
	private String name;	
	
	private BotProperties(String aName){
		name = aName;		
	}
	
	public String getName() {
		return name;
	}
	
}
