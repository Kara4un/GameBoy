package ru.daigr.telegram.bot;

public enum MainBotProperties implements BotProperties {
	
	TG_PORT("tgPort"),
	TELEGRAM_HOST("tgHost");
	
	private String name;	
	
	private MainBotProperties(String aName){
		name = aName;		
	}
	
	public String getName() {
		return name;
	}
	
}
