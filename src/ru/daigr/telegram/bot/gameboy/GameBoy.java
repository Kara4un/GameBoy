package ru.daigr.telegram.bot.gameboy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.Logger;

import ru.daigr.telegram.bot.DzrBot;
import ru.daigr.telegram.bot.PropertiesManager;
import ru.daigr.telegram.bot.Command.SendMessageCommand;
import ru.daigr.telegram.bot.data.Message;
import ru.daigr.telegram.bot.data.Update;

public class GameBoy extends DzrBot {
	
	public GameBoy(PropertiesManager props, Logger aLogger) {
		super(props, aLogger);
	}

	public boolean processUpdate(Update update) {				
		if (update == null) return false;
		if (update.getMessage() == null)
			return false;
		
		long id = update.getMessage().isChat() ? update.getMessage().getChat()
				.getId() : update.getMessage().getChatUser().getId();
				
		if (Long.toString(id) != allowedId) {
			logger.info("Trying to make update from not allowed ID");
			logger.info("ID = " + id);
			return false; 
		}
		logger.info(this.getClass().getName() + " GameBot processing update");		

		switch (recognizeCommand(update)) {
		case VOID_COMMAND: {
			logger.info("Recognize command: nothing to do");
			return false;
		}
		case ENTER_CODE: {
			Message msg = update.getMessage();
			String rawText = msg.getText();
						
			if (!Long.toString(id).equals(allowedId)) {
				logger.info("Trying to make command from Id = " + id
						+ " - it's not allowed");
				return false;
			}
			
			if (rawText == null) {
				logger.error("Text of command is empty");
				return false;
			} else if (rawText.split(" ").length < 2) {
				logger.error("Number of tokens is wrong");
				return false;
			} else {
				try {
					DzzzrResponseCodes respCode = enterCode(rawText.split(" ")[1]);
					SendMessageCommand scmd = new SendMessageCommand(
							HttpClients.createDefault(), hostAddr, token, id,
							respCode.getDesc());
					return scmd.processCommand(update);
				} catch (UnsupportedEncodingException e) {
					logger.error("We encounter a problem with encoding when entering code");
					logger.error(e);
					e.printStackTrace();
				}
			}
			logger.info("try to enter code to dzzzr engine");
			return true;
		}
		default:
			logger.info("This is not command for GameBoyBot");
			return false;
		}
	}

	private DzzzrResponseCodes enterCode(String code)
			throws UnsupportedEncodingException {		
		
		logger.info("Entering code " + code);
		HttpPost httpPost = new HttpPost(dzrMainPath);

		httpPost.addHeader("Referer", dzrReferer);
		httpPost.addHeader("User-Agent", dzrUserAgent);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("log", "on"));
		nvps.add(new BasicNameValuePair("mes", ""));
		nvps.add(new BasicNameValuePair("legend", ""));
		nvps.add(new BasicNameValuePair("nostat", ""));
		nvps.add(new BasicNameValuePair("notext", ""));
		nvps.add(new BasicNameValuePair("refresh", "0"));
		nvps.add(new BasicNameValuePair("bonus", ""));
		nvps.add(new BasicNameValuePair("kladMap", ""));
		nvps.add(new BasicNameValuePair("notags", ""));
		nvps.add(new BasicNameValuePair("cod", code));
		nvps.add(new BasicNameValuePair("action", "entcod"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));

		for (int i = 0; i < 3; i++) {
			try {
				CloseableHttpResponse response = httpClient.execute(target,
						httpPost, localContext);				
				if (response != null) {										
					if (response.getStatusLine().getStatusCode() == 401) {
						logger.info("Entering code error, need authorization");
						response.close();
						if (!makeAuthirization())
							return DzzzrResponseCodes.CANNOT_AUTHORIZE;
					} else if (response.getStatusLine().getStatusCode() == 302) {
						DzzzrResponseCodes ret = getResultCode(response);
						logger.info("Result code is: " + ret.getDesc());
						response.close();
						return ret;
					} else {
						logger.info("Unknown response");
						response.close();
					}
				}

			} catch (ClientProtocolException e) {
				logger.error("ClientProtocolException");
				logger.error(e);
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("IOException");
				logger.error(e);
				e.printStackTrace();			
			}
		}

		return DzzzrResponseCodes.DEFAULT;
	}
	
	private DzzzrResponseCodes getResultCode(CloseableHttpResponse response) {
		List<NameValuePair> nvps = URLEncodedUtils.parse(response
				.getFirstHeader("location").getValue(), Charset
				.defaultCharset());
		NameValuePair code = getNameValuePair("err", nvps);
		if (code == null) {
			return DzzzrResponseCodes.DEFAULT;
		} else {
			return DzzzrResponseCodes.find(Integer.parseInt(code.getValue()));
		}
	}

	private NameValuePair getNameValuePair(String name, List<NameValuePair> nvps) {
		for (NameValuePair nvp : nvps) {
			if (nvp.getName().equals(name)) {
				return nvp;
			}
		}
		return null;
	}
}
