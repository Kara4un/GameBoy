package ru.daigr.telegram.bot;

public enum BotProperties {

	VOID(""),
	TELEGRAM_HOST("telegramHost"),
	TOKEN("token"),
	DONOR_ID_LIST("donorIdList"),
	RECEPIENT_ID_LIST("recepientIdList"),
	
	USER("user"),
	PASSWORD("password"),
	TEAM_LOGIN("teamLogin"),
	PIN("pin"),
	DZR_HOST("dzrHost"),
	DZR_PORT("dzrPort"),
	DZR_MAIN_PATH("dzrMainPath"),
	DZR_REFERER("dzrReferer"),
	DZR_USER_AGENT("dzrUserAgent");
	
	private String name;	
	
	private BotProperties(String aName){
		name = aName;		
	}
	
	public String getName() {
		return name;
	}
	
}
