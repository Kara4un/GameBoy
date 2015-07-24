package ru.daigr.telegram.bot.data;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.daigr.telegram.bot.data.parse.json.GroupChatTags;
import ru.daigr.telegram.bot.data.parse.json.MessageTags;
import ru.daigr.telegram.bot.data.parse.json.PhotoSizeTags;
import ru.daigr.telegram.bot.data.parse.json.UpdateTags;
import ru.daigr.telegram.bot.data.parse.json.UserTags;

public class JSONDataFormatter {
	
	public static String asJSONString(Update update){
		return asJSONObject(update).toString();
	}
	
	public static String asJSONString(Message message){
		return asJSONObject(message).toString();
	}
	
	public static String asJSONString(User user){
		
		return asJSONObject(user).toString();
	}
	
	public static String asJSONString(GroupChat groupChat){		
		return asJSONObject(groupChat).toString();
	}
	
	private static JSONObject asJSONObject(Update update) {
		
		JSONObject jsonRet = new JSONObject();
		
		jsonRet.put(UpdateTags.UPDATE_ID.toString(), update.getId());
		jsonRet.put(UpdateTags.MESSAGE.toString(), update.getMessage());
		
		return jsonRet;		
	}
	
	private static JSONObject asJSONObject(Message message) {
		
		JSONObject jsonRet = asJSONNoReplyObject(message);
		
		if (message.getReplayToMessage() != null){
			jsonRet.put(MessageTags.REPLY_TO_MESSAGE.toString(), 
					asJSONNoReplyObject(message.getReplayToMessage()));
		}				
		
		return jsonRet;
		
	}
	
	private static JSONObject asJSONNoReplyObject(Message message) {
		
	JSONObject jsonRet = new JSONObject();
	
	jsonRet.put(MessageTags.MESSAGE_ID.toString(), message.getMessage_id())
	.put(MessageTags.FROM.toString(), asJSONObject(message.getFrom()))
	.put(MessageTags.DATE.toString(), message.getDate())
	.put(MessageTags.CHAT.toString(), message.isChat() ? 
			asJSONObject(message.getChat()) : asJSONObject(message.getChatUser()));
	
	if (message.getForwardFrom() != null)
	{
		jsonRet.put(MessageTags.FORWARD_FROM.toString(), asJSONObject(message.getForwardFrom()));		
		jsonRet.put(MessageTags.FORWARD_DATE.toString(), message.getForwardDate());
	}
		
	jsonRet.put(MessageTags.TEXT.toString(), message.getText());
	if (message.getNewChatParticipant() != null)
	jsonRet.put(MessageTags.NEW_CHAT_PARTICIPANT.toString(), asJSONObject(message.getNewChatParticipant()));
	if (message.getLeftChatParticipant() != null)
	jsonRet.put(MessageTags.LEFT_CHAT_PARTICIPANT.toString(), asJSONObject(message.getLeftChatParticipant()));
	if (message.getPhotoSize() != null || message.getPhotoSize().length > 0)
	jsonRet.put(MessageTags.PHOTO.toString(), asJSONArray(message.getPhotoSize()));		
	
	return jsonRet;
		
	}

	private static JSONObject asJSONObject(GroupChat groupChat) {
		JSONObject jsonRet = new JSONObject();
		
		jsonRet.put(GroupChatTags.ID.toString(), groupChat.getId());
		jsonRet.put(GroupChatTags.TITLE.toString(), groupChat.getTitle());
		
		return jsonRet;
	}

	private static JSONObject asJSONObject(User user) {
		
		JSONObject jsonRet = new JSONObject();
		
		jsonRet.put(UserTags.ID.toString(), user.getId());
		jsonRet.put(UserTags.FIRST_NAME.toString(), user.getFirstName());
		
		if (user.getLastName() != null || !user.getLastName().isEmpty())
		jsonRet.put(UserTags.LAST_NAME.toString(), user.getLastName());
		if (user.getUserName() != null || !user.getUserName().isEmpty())
		jsonRet.put(UserTags.USERNAME.toString(), user.getUserName());		
		
		return jsonRet;
		
	}
	
	private static JSONArray asJSONArray(PhotoSize[] photoSize){
		JSONArray ret = new JSONArray();
		
		for (PhotoSize pS : photoSize){
			JSONObject toAdd = new JSONObject();
			
			toAdd.put(PhotoSizeTags.ID.toString(), pS.getFileId())
			.put(PhotoSizeTags.WIDTH.toString(), pS.getWidth())
			.put(PhotoSizeTags.HEIGHT.toString(), pS.getHeight())
			.put(PhotoSizeTags.FILE_SIZE.toString(), pS.getFile_size());
			
			ret.put(toAdd);
			
		}
		
		return ret;
	}
	

}
