package ru.daigr.telegram.bot;

import ru.daigr.telegram.bot.Command.Command;
import ru.daigr.telegram.bot.data.Update;

public interface Bot {

	public boolean processUpdate(Update update);
	
}
