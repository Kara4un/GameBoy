package ru.daigr.telegram.bot.data.parse;

import ru.daigr.telegram.bot.data.GroupChat;
import ru.daigr.telegram.bot.data.Message;
import ru.daigr.telegram.bot.data.Update;
import ru.daigr.telegram.bot.data.User;

public interface IDataParser {
		
	public Update parseUpdate(String src);
	public Message parseMessage(String src);
	public GroupChat parseGroupChat(String src);
	public User parseUser(String src);
}
