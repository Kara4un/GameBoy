package ru.daigr.telegram.bot.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.logging.log4j.Logger;

import ru.daigr.telegram.bot.data.Message;
import ru.daigr.telegram.bot.data.Update;

public class ForwardCommand implements Command {
	
	private static String METHOD = "forwardMessage";
	private long toChatId = 0;
	
	private HttpClient client;
	private String hostName;
	private String botName;
	
	private enum Param {
		CHAT_ID("chat_id"),
		FROM_CHAT_ID("from_chat_id"),
		MESSAGE_ID("message_id");
		
		private String name;
		
		private Param(String aName){
			name = aName;
		}
		
		public String toString(){
			return name;
		}
	}
	
	public ForwardCommand(HttpClient aClient, String aHostName, 
			String aBotName, long aToChatId){
		client = aClient;
		hostName = aHostName;
		botName = aBotName;
		toChatId = aToChatId;				
	}

	@Override
	public boolean processCommand(Update update) {				
		
		Message m = update.getMessage();
		
		HttpPost post = new HttpPost (hostName + "/"
				+ botName + "/"
				+ METHOD);
		
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair(Param.CHAT_ID.toString(), Long.toString(toChatId)));
		urlParameters.add(new BasicNameValuePair(Param.FROM_CHAT_ID.toString(), 
				Long.toString(m.isChat() ? m.getChat().getId() : m.getChatUser().getId())));
		urlParameters.add(new BasicNameValuePair(Param.MESSAGE_ID.toString(), Long.toString(m.getMessage_id())));		
	 
		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}
		
		HttpResponse response;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {			
			e.printStackTrace();
			return false;
		} catch (IOException e) {			
			e.printStackTrace();
			return false;
		}
		System.out.println("Response Code : " 
	                + response.getStatusLine().getStatusCode());
	 
		BufferedReader rd;
		try {
			rd = new BufferedReader(
			        new InputStreamReader(response.getEntity().getContent()));
		} catch (UnsupportedOperationException e) {		
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	 
		StringBuffer result = new StringBuffer();
		String line = "";
		try {
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) { 
			e.printStackTrace();
			return false;
		}
		return true;
	}
		

}
