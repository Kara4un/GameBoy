package ru.daigr.telegram.bot;

import ru.daigr.telegram.bot.data.Update;
import ru.daigr.telegram.bot.data.parse.IDataParser;

public class GameBoy implements Bot{
	
	private String token = "";
	private String hostAddr = "";	
	
	public GameBoy(PropertiesManager props, IDataParser aDataParser) {
		token = props.getPropertie(BotProperties.TOKEN);
		hostAddr = props.getPropertie(BotProperties.TELEGRAM_HOST);							
	}	
	
	public boolean processUpdate(Update update){
		return false;
	}
	
		
}
