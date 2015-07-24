package ru.daigr.telegram.bot.Command;

import ru.daigr.telegram.bot.data.Update;

public interface Command {
		
	public boolean processCommand(Update update);
	
}
