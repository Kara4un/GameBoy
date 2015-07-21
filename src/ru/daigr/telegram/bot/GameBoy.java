package ru.daigr.telegram.bot;

import ru.daigr.network.http.server.HTTPRequest;
import ru.daigr.network.http.server.HTTPResponse;
import ru.daigr.network.http.server.IRequestProcessor;

public class GameBoy implements IRequestProcessor{
	
	private String token = "";
	private String hostAddr = "";
	private IRequestProcessor webProcessor;
	
	
	
	public GameBoy(PropertiesManager props) {
		token = props.getPropertie(BotProperties.TOKEN);
		hostAddr = props.getPropertie(BotProperties.TELEGRAM_HOST);		
	}
	
	public void setRequestProcessor(IRequestProcessor requestProcessor) {
		this.webProcessor = requestProcessor;
	}

	@Override
	public HTTPResponse processRequest(HTTPRequest aRequest) {
		return (webProcessor != null) ? webProcessor.processRequest(aRequest) : null;
	}

}
