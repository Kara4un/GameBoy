package ru.daigr.telegram.bot;

import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.scene.chart.Chart;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.Logger;

import ru.daigr.telegram.bot.Command.Command;
import ru.daigr.telegram.bot.Command.ForwardCommand;
import ru.daigr.telegram.bot.data.GroupChat;
import ru.daigr.telegram.bot.data.Update;

public class BlahBot implements Bot {
	
	public enum TelegramComands {
				
		ADD_AS_DONOR("/capture_on"),
		DELETE_FROM_DONORS("/capture_off"),
		ADD_AS_RECEPIENT("/translation_on"),
		DELETE_RECEPIENT("/translation_off"),		
		PHOTO_REPLY_COMMAND(""),
		VOID_COMMAND("");
		
		private String command = "";
		
		private TelegramComands(String aCommand){
			command = aCommand;
		}
		
		public String getCommand(){
			return command;
		}
	}
	
	private Predicate<String> isStrInvalid = (a) -> {
		return a == null || a.isEmpty(); 
	};		
	
	private CloseableHttpClient httpClient;
	private ArrayList<Long> chatsToForward = new ArrayList<>();
	private ArrayList<Long> chatsFromForward = new ArrayList<>();
	
	private String hostAddr = "";
	private String botName = "";		
	
	private Logger logger;
	PropertiesManager props;
	
	public BlahBot(PropertiesManager aProps, Logger aLogger) {			
		props = aProps;
		logger = aLogger;
		
		if (props != null){
			botName = props.getPropertie(BotProperties.TOKEN);
			hostAddr = props.getPropertie(BotProperties.TELEGRAM_HOST);						
						
			if (!isStrInvalid.test(aProps.getPropertie(BotProperties.DONOR_ID_LIST))){
				initIdsFromStr(aProps.getPropertie(BotProperties.DONOR_ID_LIST), chatsToForward);
			}
			if (!isStrInvalid.test(aProps.getPropertie(BotProperties.RECEPIENT_ID_LIST))){
				initIdsFromStr(aProps.getPropertie(BotProperties.RECEPIENT_ID_LIST), chatsFromForward);
			}
		}						
				
		httpClient = HttpClients.createDefault();		
	}
	
	public boolean processUpdate(Update update){				
		
		logger.info(this.getClass().getName() + " processing update");
		if (update.getMessage() == null) return false;				
		
		switch (recognizeCommand(update)){
			case VOID_COMMAND : {
				logger.info("Recognize command: nothing to do");
				return false;
			}
			case ADD_AS_DONOR :{	
				logger.info("Adding id as donor chat");				
				addAsDonor(update);
				return true;
			}
			case DELETE_FROM_DONORS :{
				logger.info("Delete id from donor chat list");
				deleteFromDonors(update);
				return true;
			}
			case PHOTO_REPLY_COMMAND :{
				return processPhotosForward(update);				
			}
			case ADD_AS_RECEPIENT:{
				logger.info("Adding id as recepient chat");
				addAsRecepient(update);
				return true;
			}			
			case DELETE_RECEPIENT:{
				logger.info("Delete id from recepient chat list");
				deleteFromRecepients(update);
				return true;
			}											
		}	
		
		return false;
	}		
	
	private boolean processPhotosForward(Update update){
										
		logger.info(this.getClass().getName() + " processing photos FORWARD, send message");				
		boolean ret = false;				
		
		ForwardCommand forward;
				
		for (Long cId : chatsToForward){			
			logger.info(this.getClass().getName() + " processing update to chat id="
					+ cId);
			forward = new ForwardCommand(httpClient, hostAddr, 
					botName, cId);
			ret = forward.processCommand(update);			
		}
		
		return ret;
	}
	
	private void addAsDonor(Update update){
		if (update.getMessage() == null) return;
		long id = update.getMessage().isChat() ? 
				update.getMessage().getChat().getId() :
				update.getMessage().getChatUser().getId();
		for (Long l : chatsToForward){
			if (l == id) return;
		}
		for (Long l : chatsFromForward){
			if (l == id) return;
		}			
		chatsFromForward.add(id);
		props.setPropertie(BotProperties.DONOR_ID_LIST, arrayAsString(chatsFromForward));
	}
	
	private void addAsRecepient(Update update){
		if (update.getMessage() == null) return;
		long id = update.getMessage().isChat() ? 
				update.getMessage().getChat().getId() :
				update.getMessage().getChatUser().getId();
		for (Long l : chatsToForward){
			if (l == id) return;
		}
		for (Long l : chatsFromForward){
			if (l == id) return;
		}			
		chatsToForward.add(id);
		props.setPropertie(BotProperties.RECEPIENT_ID_LIST, arrayAsString(chatsToForward));
	}
	
	private void deleteFromDonors(Update update){
		if (update.getMessage() == null) return;
		long id = update.getMessage().isChat() ? 
				update.getMessage().getChat().getId() :
				update.getMessage().getChatUser().getId();
				
		chatsFromForward.remove(new Long(id));
		props.setPropertie(BotProperties.DONOR_ID_LIST, arrayAsString(chatsFromForward));
	}
	
	private void deleteFromRecepients(Update update){
		if (update.getMessage() == null) return;
		long id = update.getMessage().isChat() ? 
				update.getMessage().getChat().getId() :
				update.getMessage().getChatUser().getId();
				
		chatsToForward.remove(new Long(id));
		props.setPropertie(BotProperties.RECEPIENT_ID_LIST, arrayAsString(chatsFromForward));
	}				
	
	private TelegramComands recognizeCommand(Update update){
		if (update.getMessage() == null) return TelegramComands.VOID_COMMAND;
		
		String src = update.getMessage().getText();
		if (!(src == null || src.isEmpty())){
			for (TelegramComands command : TelegramComands.values()){
				if (src.startsWith(command.getCommand())) return command;
			}
		}				
		
		if (update.getMessage().getPhotoSize() != null &&
				update.getMessage().getPhotoSize().length > 0){
			if (chatsFromForward.contains(
					update.getMessage().isChat() ? 
							update.getMessage().getChat().getId() :
								update.getMessage().getChatUser().getId())){
				return TelegramComands.PHOTO_REPLY_COMMAND;
			}			
		}
		
		return TelegramComands.VOID_COMMAND;
	}
	
	private void initIdsFromStr(String str, ArrayList<Long> list){
		String[] src = str.split(";");
		for (String s : src){
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
