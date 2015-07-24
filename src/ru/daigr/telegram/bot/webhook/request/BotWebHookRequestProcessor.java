package ru.daigr.telegram.bot.webhook.request;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import ru.daigr.network.http.server.HTTPRequest;
import ru.daigr.network.http.server.HTTPResponse;
import ru.daigr.network.http.server.IRequestProcessor;
import ru.daigr.telegram.bot.Bot;
import ru.daigr.telegram.bot.data.Update;
import ru.daigr.telegram.bot.data.parse.IDataParser;

public class BotWebHookRequestProcessor implements IRequestProcessor{
	
	IDataParser dataFactory;	
	ArrayList<Bot> bots = new ArrayList<>();
	Logger logger;
	
	public BotWebHookRequestProcessor(IDataParser aDataFactory, Logger aLogger) {
		dataFactory = aDataFactory;
		logger = aLogger;
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
		logger.info("Parsing request body: " + aRequest.getBody());
		Update update = dataFactory.parseUpdate(aRequest.getBody());
		for (Bot aBot : bots){
			logger.info("Process update with bot: " + aBot.getClass().getName());
			aBot.processUpdate(update);
		}
		return new HTTPResponse();
	}
	

}
