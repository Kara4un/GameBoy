package ru.daigr.telegram.bot.webhook.request;

import ru.daigr.network.http.server.HTTPRequest;
import ru.daigr.network.http.server.HTTPResponse;
import ru.daigr.network.http.server.IRequestProcessor;
import ru.daigr.telegram.bot.data.parse.IDataParser;

public class BotWebHookRequestProcessor implements IRequestProcessor{
	
	IDataParser dataFactory;		
	
	public BotWebHookRequestProcessor(IDataParser aDataFactory) {
		dataFactory = aDataFactory;		
	}

	@Override
	public HTTPResponse processRequest(HTTPRequest aRequest) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
