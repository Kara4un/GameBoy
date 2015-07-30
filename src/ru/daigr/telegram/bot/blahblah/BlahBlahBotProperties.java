package ru.daigr.telegram.bot.blahblah;

import ru.daigr.telegram.bot.BotProperties;

public enum BlahBlahBotProperties implements BotProperties {
	
	VOID(""),	
	TOKEN("blahBlahToken"),
	DONOR_ID_LIST("donorIdList"),
	RECEPIENT_ID_LIST("recepientIdList");		
	
	private String name;	
	
	private BlahBlahBotProperties(String aName){
		name = aName;		
	}
	
	public String getName() {
		return name;
	}

}
