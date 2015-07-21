package ru.daigr.telegram.bot;

public enum BotProperties {

	VOID(""),
	TELEGRAM_HOST("telegramHost"),
	TOKEN("token");
	
	private String name;	
	
	private BotProperties(String aName){
		name = aName;		
	}
	
	public String getName() {
		return name;
	}
	
}
