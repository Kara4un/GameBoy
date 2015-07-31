package ru.daigr.telegram.bot.hint;

import ru.daigr.telegram.bot.BotProperties;

public enum HintBotProperties implements BotProperties{ 

	NEED_AUTH_FLAG("needAuthFlag"),
	ALLOWED_CHATS("lastHintChats"),
	
	TOKEN("hintToken"),	
	
	LAST_HINT_NUMBER("lastHintNumber"),
	LAST_HINT_GAME("lastHintGame"),	
	LAST_HINT_MESSION("lastHintMission");	
	
	private String name;	
	
	private HintBotProperties(String aName){
		name = aName;		
	}
	
	public String getName() {
		return name;
	}

}
