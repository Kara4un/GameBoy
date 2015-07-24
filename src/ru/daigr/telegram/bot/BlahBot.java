package ru.daigr.telegram.bot;

import java.util.ArrayList;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import ru.daigr.telegram.bot.Command.Command;
import ru.daigr.telegram.bot.Command.ForwardCommand;
import ru.daigr.telegram.bot.data.GroupChat;
import ru.daigr.telegram.bot.data.Update;

public class BlahBot implements Bot {	
	
	private static String chatToReplay = "photos";
	
	private CloseableHttpClient httpClient;
	private ArrayList<GroupChat> availableChats = new ArrayList<>(); 
	
	private String hostAddr = "";
	private String botName = "";
	
	public BlahBot(PropertiesManager props) {			
		
		if (props != null){
			botName = props.getPropertie(BotProperties.TOKEN);
			hostAddr = props.getPropertie(BotProperties.TELEGRAM_HOST);
		}
		if (!props.getPropertie(BotProperties.PERMANENT_CHAT_ID).isEmpty() &&
				!props.getPropertie(BotProperties.PERMANENT_CHAT_NAME).isEmpty()){
			GroupChat permanentChat = new GroupChat(Long.getLong(props.getPropertie(BotProperties.PERMANENT_CHAT_ID)));
			permanentChat.setTitle(props.getPropertie(BotProperties.PERMANENT_CHAT_NAME));
			availableChats.add(permanentChat);
		}
		if (!props.getPropertie(BotProperties.CHAT_TO_REPLY).isEmpty()){
			chatToReplay = props.getPropertie(BotProperties.CHAT_TO_REPLY);
		}
				
		httpClient = HttpClients.createDefault();		
	}	
	
	public boolean processUpdate(Update update){				
		
		if (update.getMessage() == null) return false;
		
		if (update.getMessage().isChat()){
			addToChats(update.getMessage().getChat());
			if (chatToReply(update.getMessage().getChat())) return false;
		}
		
		return processPhotosReply(update);
	}
	
	private boolean processPhotosReply(Update update){
		boolean ret = false;
		
		for (GroupChat gC : availableChats){
			if (chatToReply(gC)){
				Command forward = new ForwardCommand(httpClient, hostAddr, 
						botName, gC.getId());
				ret = forward.processCommand(update);
			}
		}
		
		return ret;
	}
	
	private boolean chatToReply(GroupChat chat){
		if (chat != null && chat.getTitle().contains(chatToReplay)) return true;
		return false;
	}
	
	private void addToChats(GroupChat chat){
		if (chat == null) return;
		for (GroupChat gC : availableChats){
			if (chat.equals(gC)) return;
		}
		availableChats.add(chat);
	}

}
