package ru.daigr.telegram.bot.webhook.request;

import java.util.ArrayList;

import ru.daigr.network.http.server.HTTPRequest;
import ru.daigr.network.http.server.HTTPResponse;
import ru.daigr.network.http.server.IRequestProcessor;
import ru.daigr.telegram.bot.Bot;
import ru.daigr.telegram.bot.data.Update;
import ru.daigr.telegram.bot.data.parse.IDataParser;

public class BotWebHookRequestProcessor implements IRequestProcessor{
	
	IDataParser dataFactory;	
	ArrayList<Bot> bots = new ArrayList<>();
	
	public BotWebHookRequestProcessor(IDataParser aDataFactory) {
		dataFactory = aDataFactory;
		
	}
	
	public void addBot(Bot aBot){
		bots.add(aBot);
	}
	
	public void removeBot(Bot aBot){
		bots.remove(aBot);
	}
	
	public void resetBots(){
		bots.clear();
	}
	

	@Override
	public HTTPResponse processRequest(HTTPRequest aRequest) {	
		Update update = dataFactory.parseUpdate(aRequest.getBody());
		for (Bot aBot : bots){
			aBot.processUpdate(update);
		}
		return new HTTPResponse();
	}
	

}
