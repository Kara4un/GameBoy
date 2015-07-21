package ru.daigr.telegram.bot.webhook.request;

import ru.daigr.network.http.server.HTTPRequest;
import ru.daigr.network.http.server.HTTPResponse;
import ru.daigr.network.http.server.IResponseProcessor;
import ru.daigr.telegram.bot.data.parse.IDataParser;

public class BotWebHookRequestProcessor implements IResponseProcessor{
	
	IDataParser dataFactory;		
	
	public BotWebHookRequestProcessor(IDataParser aDataFactory) {
		dataFactory = aDataFactory;		
	}

	@Override
	public HTTPResponse buildResponse(HTTPRequest aRequest) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
