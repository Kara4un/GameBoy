package ru.daigr.telegram.bot.data.parse;

import java.util.function.Predicate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.daigr.telegram.bot.data.GroupChat;
import ru.daigr.telegram.bot.data.Message;
import ru.daigr.telegram.bot.data.Update;
import ru.daigr.telegram.bot.data.User;


public class JSONDataParser implements IDataParser {
	
	Predicate<String> isJSON = (str) -> {
		try {
	        new JSONObject(str);
	    } catch (JSONException ex) {	        
	        try {
	            new JSONArray(str);
	        } catch (JSONException ex1) {
	            return false;
	        }
	    }
	    return true;		
	};

	@Override
	public Update parseUpdate(String src) {
		if (!isJSON.test(src)) return Update.defultUpdate();
		return null;
	}

	@Override
	public Message parseMessage(String src) {
		if (!isJSON.test(src)) return Message.defaultMessage();
		return null;
	}

	@Override
	public GroupChat parseGroupChat(String src) {
		if (!isJSON.test(src)) return GroupChat.defaultGroupChat();
		return null;
	}

	@Override
	public User parseUser(String src) {
		if (!isJSON.test(src)) return User.defaultUser();
		return null;
	}

}
