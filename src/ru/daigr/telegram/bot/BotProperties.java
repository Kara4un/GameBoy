package ru.daigr.telegram.bot;

public enum BotProperties {

	VOID(""),
	TELEGRAM_HOST("telegramHost"),
	TOKEN("token"),
	DONOR_ID_LIST("donorIdList"),
	RECEPIENT_ID_LIST("recepientIdList");
	
	private String name;	
	
	private BotProperties(String aName){
		name = aName;		
	}
	
	public String getName() {
		return name;
	}
	
}
