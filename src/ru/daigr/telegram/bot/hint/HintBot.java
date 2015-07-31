package ru.daigr.telegram.bot.hint;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Logger;

import ru.daigr.telegram.bot.DzrBot;
import ru.daigr.telegram.bot.PropertiesManager;
import ru.daigr.telegram.bot.Command.SendMessageCommand;
import ru.daigr.telegram.bot.blahblah.BlahBlahBotProperties;
import ru.daigr.telegram.bot.data.Update;

public class HintBot extends DzrBot implements Runnable {	
	
	private DzzzrPageParser parser;
	private Hint currentLastHint;
	private PropertiesManager props;
	private ArrayList<Long> availableChats; 

	public HintBot(PropertiesManager aProps, Logger aLogger) {
		super(aProps,aLogger);		
		
		if (aProps!=null) {
			props = aProps;
			token = props.getPropertie(HintBotProperties.TOKEN);
			parser = new DzzzrPageParser(props.getPropertie(HintBotProperties.NEED_AUTH_FLAG));
			currentLastHint = new Hint();
			currentLastHint.setGameName(props.getPropertie(HintBotProperties.LAST_HINT_GAME));
			currentLastHint.setHintNumber(props.getPropertie(HintBotProperties.LAST_HINT_NUMBER));
			currentLastHint.setMissionNubmer(props.getPropertie(HintBotProperties.LAST_HINT_MESSION));
			initIdsFromStr(props.getPropertie(HintBotProperties.ALLOWED_CHATS), availableChats);
		}		
	}

	public boolean processUpdate(Update update) {

		logger.info(this.getClass().getName() + " HintBot processing update");
		if (update == null)
			return false;
		if (update.getMessage() == null)
			return false;		
		switch (recognizeCommand(update)) {		
		case GET_LAST_HINT: {					
			long id = update.getMessage().isChat() ? update.getMessage().getChat()
					.getId() : update.getMessage().getChatUser().getId();
			logger.info("Sending last hint to id: " + id);
			logger.info("hint is: " + currentLastHint.printHint());
			SendMessageCommand scmd = new SendMessageCommand(
					HttpClients.createDefault(), hostAddr, token,id,
					currentLastHint.printHint());
			scmd.processCommand(null);
		}
		case SUBCRIBE: {
			subscribe(update);
		}
		case UNSUBSCRIBE: {
			unsubscribe(update);
		}
		default:
			logger.info("This is not command for HintBot");
			return false;
		}
	}

	private Hint getHint()
			throws UnsupportedEncodingException {					
				
		HttpGet httpGet = new HttpGet(dzrMainPath);

		httpGet.addHeader("Referer", dzrReferer);
		httpGet.addHeader("User-Agent", dzrUserAgent);		//

		for (int i = 0; i < 3; i++) {
			try {
				CloseableHttpResponse response = httpClient.execute(target,
						httpGet, localContext);				
				if (response != null) {													
					switch (response.getStatusLine().getStatusCode()) {						
						case 200 : {
							String body = EntityUtils.toString(response.getEntity());
							if (body == null) {
								return null;
							} else if (parser.needAuth(body)){
								makeAuthirization();								
							} else {
								Hint hint = parser.parseLastHint(body);
								if (hint != null && !hint.equals(currentLastHint)){
									return hint;
								} else {
									return null;
								}
							}							
						}						
					}
					response.close();
				}

			} catch (ClientProtocolException e) {
				logger.error("ClientProtocolException");
				logger.error(e);
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("IOException");
				logger.error(e);
				e.printStackTrace();			
			}
		}
		return null;
	}

	@Override
	public void run() {
		
		while (true) {			
			try {
				Thread.sleep(1000);
				
				Hint current;				
				current = getHint();
				if (!current.equals(currentLastHint)){
					currentLastHint = current;
					for (long l : availableChats){
						SendMessageCommand scmd = new SendMessageCommand(
								HttpClients.createDefault(), hostAddr, token, l,
								currentLastHint.printHint());
						scmd.processCommand(null);
					}					
				}
			} catch (InterruptedException e) {
				logger.error("Interrupted exception");
				logger.error(e);
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				logger.error("UnsupportedEncodingException exception while getting hint");
				logger.error(e);
				e.printStackTrace();
			}			
		}		
	}
	
	private void subscribe(Update update){
		if (update.getMessage() == null) return;
		long id = update.getMessage().isChat() ? 
				update.getMessage().getChat().getId() :
				update.getMessage().getChatUser().getId();		
		for (Long l : availableChats){
			if (l == id) return;
		}			
		availableChats.add(id);
		props.setPropertie(HintBotProperties.ALLOWED_CHATS, arrayAsString(availableChats));
	}
	
	private void unsubscribe(Update update){
		if (update.getMessage() == null) return;
		long id = update.getMessage().isChat() ? 
				update.getMessage().getChat().getId() :
				update.getMessage().getChatUser().getId();		
		for (Long l : availableChats){
			if (l == id) return;
		}			
		availableChats.remove(id);
		props.setPropertie(HintBotProperties.ALLOWED_CHATS, arrayAsString(availableChats));
	}
	
	private void initIdsFromStr(String str, ArrayList<Long> list){
		String[] src = str.split(";");
		for (String s : src){
			if (s == null || s.isEmpty()) continue;
			list.add(Long.parseLong(s));
		}
	}
	
	private String arrayAsString(ArrayList<Long> list){
		String ret = "";
		
		for (Long l : list){
			ret+=l + ";";
		}
		return ret;
	}

}
