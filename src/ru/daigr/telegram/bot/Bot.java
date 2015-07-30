package ru.daigr.telegram.bot;

import ru.daigr.telegram.bot.data.Update;

public abstract class Bot {
	
	public enum TelegramComands {				
		
		ENTER_CODE("/enter_code"),
		GET_LAST_HINT("/get_last_hint"),
		SUBCRIBE("/subscribe"),
		UNSUBSCRIBE("/unsubscribe"),
		
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

	public abstract boolean processUpdate(Update update);
	
	protected TelegramComands recognizeCommand(Update update){
		if (update.getMessage() == null) return TelegramComands.VOID_COMMAND;
		
		String src = update.getMessage().getText();
		if (!(src == null || src.isEmpty())){
			for (TelegramComands command : TelegramComands.values()){
				if (src.startsWith(command.getCommand())) return command;
			}
		}								
		
		return TelegramComands.VOID_COMMAND;
	}
	
}
