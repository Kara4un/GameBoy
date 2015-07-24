package ru.daigr.telegram.bot;

import ru.daigr.telegram.bot.data.Update;

public class GameBoy implements Bot{
	
	private String token = "";
	private String hostAddr = "";	
	
	public GameBoy(PropertiesManager props) {
		if (props != null){
			token = props.getPropertie(BotProperties.TOKEN);
			hostAddr = props.getPropertie(BotProperties.TELEGRAM_HOST);
		}								
	}	
	
	public boolean processUpdate(Update update){
		return false;
	}
	
		
}
