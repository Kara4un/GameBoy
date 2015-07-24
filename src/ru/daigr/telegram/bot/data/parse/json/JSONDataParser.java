package ru.daigr.telegram.bot.data.parse.json;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Predicate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.daigr.telegram.bot.data.GroupChat;
import ru.daigr.telegram.bot.data.Message;
import ru.daigr.telegram.bot.data.PhotoSize;
import ru.daigr.telegram.bot.data.Update;
import ru.daigr.telegram.bot.data.User;
import ru.daigr.telegram.bot.data.parse.IDataParser;


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
	
	Predicate<String> isJSONObject = (str) -> {
		try {
	        new JSONObject(str);
	    } catch (JSONException ex) {	        
	        return false;
	    }
	    return true;		
	};
	
	Predicate<String> isJSONArray = (str) -> {
		try {
	        new JSONArray(str);
	    } catch (JSONException ex) {	        	        
	        return false;	        
	    }	    return true;		
	};

	
	@Override
	public Update parseUpdate(String src) {
		if (!isJSONObject.test(src)) return Update.defultUpdate();
		
		try {
		JSONObject jsonUpdate = new JSONObject(src);				
		Update update = new Update(jsonUpdate.getLong(UpdateTags.UPDATE_ID.toString()), 
				parseJSONMessage(jsonUpdate.getJSONObject(UpdateTags.MESSAGE.toString())));				
		
		return update;
		} catch (JSONException jE){
			jE.printStackTrace();
			return Update.defultUpdate();
		} 
	}

	@Override
	public Message parseMessage(String src) {
		if (!isJSON.test(src)) return Message.defaultMessage();
		try {
			return parseJSONMessage(new JSONObject(src));
		} catch (JSONException jE){
			jE.printStackTrace();
			return Message.defaultMessage();
		}
		
	}

	@Override
	public GroupChat parseGroupChat(String src) {
		if (!isJSON.test(src)) return GroupChat.defaultGroupChat();
		try{
			return parseJSONGroupChat(new JSONObject(src));
		} catch (JSONException jE){
			jE.printStackTrace();
			return GroupChat.defaultGroupChat();
		}
	}

	@Override
	public User parseUser(String src) {
		if (!isJSON.test(src)) return User.defaultUser();
		try {
		return parseJSONUser(new JSONObject(src));
		} catch (JSONException jE){
			jE.printStackTrace();
			return User.defaultUser();
		}
	}		
	
	private Message parseJSONMessage (JSONObject jsonMessage)  throws JSONException {
		
		Message ret = parseJSONNonReplayMessage(jsonMessage);		
		if (jsonMessage.has(MessageTags.REPLY_TO_MESSAGE.toString()))
		ret.setReplayToMessage(parseJSONNonReplayMessage(jsonMessage.getJSONObject(MessageTags.REPLY_TO_MESSAGE.toString())));
		
		return ret;
	}
	
	public GroupChat parseJSONGroupChat(JSONObject jsonGroupChat) throws JSONException {
		
		long id = jsonGroupChat.getLong(GroupChatTags.ID.toString());
		GroupChat ret = new GroupChat(id);
				
		ret.setTitle(jsonGroupChat.getString(GroupChatTags.TITLE.toString()));
		
		return ret;
	}
	
	public User parseJSONUser(JSONObject jU) throws JSONException {	
		
		long id = jU.getLong(UserTags.ID.toString());
		User ret = new User(id);
		
		ret.setFirstName(jU.getString(UserTags.FIRST_NAME.toString()));
		
		if (jU.has(UserTags.LAST_NAME.toString()))
		ret.setLastName(jU.getString(UserTags.LAST_NAME.toString()));
		if (jU.has(UserTags.USERNAME.toString()))
		ret.setUserName(jU.getString(UserTags.USERNAME.toString()));
		
		return ret;
	}
	
	private Message parseJSONNonReplayMessage(JSONObject jM) throws JSONException {
		
		long id = jM.getLong(MessageTags.MESSAGE_ID.toString());
		Message ret = new Message(id);
				
		ret.setFrom(parseJSONUser(jM.getJSONObject(MessageTags.FROM.toString())));
		ret.setDate(jM.getLong(MessageTags.DATE.toString()));
		
		try {
			ret.setChat(parseJSONGroupChat(jM.getJSONObject(MessageTags.CHAT.toString())));
			ret.setChat(true);
		} catch (JSONException jE){
			ret.setChatUser(parseJSONUser(jM.getJSONObject(MessageTags.CHAT.toString())));
			ret.setChat(false);
		}
		
				
		if (jM.has(MessageTags.FORWARD_FROM.toString()))
		ret.setForwardFrom(parseJSONUser(jM.getJSONObject(MessageTags.FORWARD_FROM.toString())));
		if (jM.has(MessageTags.FORWARD_DATE.toString()))
		ret.setForwardDate(jM.getLong(MessageTags.FORWARD_DATE.toString()));

		if (jM.has(MessageTags.TEXT.toString()))
		ret.setText(jM.getString(MessageTags.TEXT.toString()));
		
		if (jM.has(MessageTags.NEW_CHAT_PARTICIPANT.toString()))
		ret.setNewChatParticipant(parseJSONUser(jM.getJSONObject(MessageTags.NEW_CHAT_PARTICIPANT.toString())));
		if (jM.has(MessageTags.LEFT_CHAT_PARTICIPANT.toString()))
		ret.setLeftChatParticipant(parseJSONUser(jM.getJSONObject(MessageTags.LEFT_CHAT_PARTICIPANT.toString())));
		
		if (jM.has(MessageTags.PHOTO.toString()))
		ret.setPhotoSize(parseJSONPhotoSize(jM.getJSONArray(MessageTags.PHOTO.toString())));
		
		return ret;
	}		
	
	private PhotoSize[] parseJSONPhotoSize(JSONArray jP) {
		ArrayList<PhotoSize> ret = new ArrayList<>();
		
		for (int i = 0; i < jP.length(); i++){
			JSONObject toAdd = jP.getJSONObject(i);
			
			String id = toAdd.getString(PhotoSizeTags.ID.toString());
			PhotoSize tmp = new PhotoSize(id);
			
			tmp.setWidth(toAdd.getLong(PhotoSizeTags.WIDTH.toString()));
			tmp.setHeight(toAdd.getLong(PhotoSizeTags.HEIGHT.toString()));
			tmp.setFile_size(toAdd.getLong(PhotoSizeTags.FILE_SIZE.toString()));
			
			ret.add(tmp);
		}
		
		return ret.toArray(new PhotoSize[]{});
	}

}
