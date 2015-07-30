package ru.daigr.telegram.bot;

public enum DzrBotProperties implements BotProperties {
	
	VOID(""),	
	TOKEN("dzrBotToken"),				
		
	USER("user"),
	PASSWORD("password"),
	TEAM_LOGIN("teamLogin"),
	PIN("pin"),
	DZR_HOST("dzrHost"),
	DZR_PORT("dzrPort"),
	DZR_MAIN_PATH("dzrMainPath"),
	DZR_REFERER("dzrReferer"),
	DZR_USER_AGENT("dzrUserAgent"),
	DZR_ALLOWED_CHAT_ID("allowedId");	
	
	private String name;	
	
	private DzrBotProperties(String aName){
		name = aName;		
	}
	
	public String getName() {
		return name;
	}

}
