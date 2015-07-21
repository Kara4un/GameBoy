package ru.daigr.telegram.bot.Command;

import ru.daigr.telegram.bot.data.Message;

public interface Command {
		
	public boolean processCommand(Message message);
	
}
