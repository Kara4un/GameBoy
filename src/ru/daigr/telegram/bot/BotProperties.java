package ru.daigr.telegram.bot;

public enum BotProperties {

	VOID(""),
	TELEGRAM_HOST("telegramHost"),
	TOKEN("token"),
	DONOR_ID_LIST("donorIdList"),
	RECEPIENT_ID_LIST("recepientIdList"),
	TG_PORT("tgPort"),
	
	LAST_HINT_NUMBER("lastHintNumber"),
	LAST_HINT_GAME("lastHintGame"),
	LAST_HINT_MESSION("lastHintMission"),
	
	
	USER("user"),
	PASSWORD("password"),
	TEAM_LOGIN("teamLogin"),
	PIN("pin"),
	DZR_HOST("dzrHost"),
	DZR_PORT("dzrPort"),
	DZR_MAIN_PATH("dzrMainPath"),
	DZR_REFERER("dzrReferer"),
	DZR_USER_AGENT("dzrUserAgent"),
	DZR_ALLOWED_CHAT_ID("allowedId"),
	NEED_AUTH_FLAG("needAuthFlag");
	
	private String name;	
	
	private BotProperties(String aName){
		name = aName;		
	}
	
	public String getName() {
		return name;
	}
	
}
