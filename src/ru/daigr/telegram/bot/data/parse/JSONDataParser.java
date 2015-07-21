package ru.daigr.telegram.bot.data.parse;

import ru.daigr.telegram.bot.data.GroupChat;
import ru.daigr.telegram.bot.data.Message;
import ru.daigr.telegram.bot.data.Update;

import com.sun.jna.platform.win32.Netapi32Util.User;

public class JSONDataParser implements IDataParser {

	@Override
	public Update parseUpdate(String src) {
		return null;
	}

	@Override
	public Message parseMessage(String src) {
		return null;
	}

	@Override
	public GroupChat parseGroupChat(String src) {
		return null;
	}

	@Override
	public User parseUser(String src) {
		return null;
	}

}
