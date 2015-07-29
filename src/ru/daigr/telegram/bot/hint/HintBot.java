package ru.daigr.telegram.bot.hint;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Logger;

import ru.daigr.telegram.bot.Bot;
import ru.daigr.telegram.bot.BotProperties;
import ru.daigr.telegram.bot.DzrBot;
import ru.daigr.telegram.bot.PropertiesManager;
import ru.daigr.telegram.bot.data.Update;
import ru.daigr.telegram.bot.gameboy.DzzzrResponseCodes;

public class HintBot extends DzrBot {	
	
	private DzzzrPageParser parser;
	private Hint currentLastHint;	

	public HintBot(PropertiesManager props, Logger aLogger) {
		super(props,aLogger);
		if (props != null) {					
			parser = new DzzzrPageParser(props.getPropertie(BotProperties.NEED_AUTH_FLAG));
			currentLastHint = new Hint();
			currentLastHint.setGameName(props.getPropertie(BotProperties.LAST_HINT_GAME));
			currentLastHint.setHintNumber(props.getPropertie(BotProperties.LAST_HINT_NUMBER));
			currentLastHint.setMissionNubmer(props.getPropertie(BotProperties.LAST_HINT_MESSION));						
		}		
	}

	public boolean processUpdate(Update update) {

		logger.info(this.getClass().getName() + " HintBot processing update");
		if (update == null)
			return false;
		if (update.getMessage() == null)
			return false;		

		switch (recognizeCommand(update)) {		
		case GET_HINT: {
			try {
					DzzzrResponseCodes respCode = getHint();					
					return false;
				} catch (UnsupportedEncodingException e) {
					logger.error("We encounter a problem with encoding when entering code");
					logger.error(e);
					e.printStackTrace();
				}
				
		}
		default:
			logger.info("This is not command for HintBot");
			return false;
		}
	}

	private DzzzrResponseCodes getHint()
			throws UnsupportedEncodingException {					
				
		HttpGet httpGet = new HttpGet(dzrMainPath);

		httpGet.addHeader("Referer", dzrReferer);
		httpGet.addHeader("User-Agent", dzrUserAgent);		//

		for (int i = 0; i < 3; i++) {
			try {
				CloseableHttpResponse response = httpClient.execute(target,
						httpGet, localContext);				
				if (response != null) {													
					switch (response.getStatusLine().getStatusCode()) {
						case 302 : {							
							break;
						}
						case 200 : {
							String body = EntityUtils.toString(response.getEntity());
							if (body == null) {
								return DzzzrResponseCodes.DEFAULT;
							} else if (parser.needAuth(body)){
								makeAuthirization();
							} else {
								Hint hint = parser.parseLastHint(body);
							}							
						}
						default : {
							
						}
					}
					response.close();
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

}
