package ru.daigr.telegram.bot;

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
import org.apache.logging.log4j.Logger;

public abstract class DzrBot extends Bot{
	
	protected CloseableHttpClient httpClient;
	protected HttpHost target;
	protected HttpClientContext localContext;
	
	protected Logger logger;

	protected String token = "";
	protected String hostAddr = "";

	protected String user = "";
	protected String password = "";
	protected String teamLogin = "";
	protected String pin = "";

	protected String dzrHost = "";
	protected String dzrPort = "";
	protected String dzrMainPath = "";
	protected String dzrReferer = "";
	protected String dzrUserAgent = "";

	protected String allowedId = "";
	
	public DzrBot(PropertiesManager props, Logger aLogger) {
		logger = aLogger;
		if (props != null) {
			token = props.getPropertie(BotProperties.TOKEN);
			hostAddr = props.getPropertie(BotProperties.TELEGRAM_HOST);

			user = props.getPropertie(BotProperties.USER);
			password = props.getPropertie(BotProperties.PASSWORD);
			teamLogin = props.getPropertie(BotProperties.TEAM_LOGIN);
			pin = props.getPropertie(BotProperties.PIN);

			dzrHost = props.getPropertie(BotProperties.DZR_HOST);
			dzrPort = props.getPropertie(BotProperties.DZR_PORT);
			dzrMainPath = props.getPropertie(BotProperties.DZR_MAIN_PATH);
			dzrReferer = props.getPropertie(BotProperties.DZR_REFERER);
			dzrUserAgent = props.getPropertie(BotProperties.DZR_USER_AGENT);
			allowedId = props.getPropertie(BotProperties.DZR_ALLOWED_CHAT_ID);								
		}

		initClient();
	}
	
	protected void initClient() {
		if (httpClient != null) {
			return;
		}

		target = new HttpHost(dzrHost, Integer.parseInt(dzrPort), "http");
		localContext = HttpClientContext.create();

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
				new AuthScope(target.getHostName(), target.getPort()),
				new UsernamePasswordCredentials(teamLogin, pin));
		httpClient = HttpClients.custom()
				.setDefaultCredentialsProvider(credsProvider).build();
		logger.info("HTTPClient для бота GameBoy успешно инициализирован."
				+ "\nDzrHost " + dzrHost + "\nDzrPort " + dzrPort
				+ "\nTeamLogin " + teamLogin + "\nPin " + pin);
	}	
	
	protected boolean makeAuthirization() throws UnsupportedEncodingException {

		AuthCache authCache = new BasicAuthCache();
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(target, basicAuth);

		CookieStore cookieStore = new BasicCookieStore();

		localContext.setAuthCache(authCache);
		localContext.setCookieStore(cookieStore);

		HttpPost httpPost = new HttpPost(dzrMainPath);

		httpPost.addHeader("Referer", dzrReferer);
		httpPost.addHeader("User-Agent", dzrUserAgent);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("notags", ""));
		nvps.add(new BasicNameValuePair("action", "auth"));
		nvps.add(new BasicNameValuePair("login", user));
		nvps.add(new BasicNameValuePair("password", password));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));

		for (int i = 0; i < 3; i++) {
			CloseableHttpResponse response = null;
			try {
				response = httpClient.execute(target, httpPost, localContext);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("Unable to close client after authorization");
					logger.error(e);
					e.printStackTrace();
				}
			}
			if (response != null) {
				List<Cookie> cookies = localContext.getCookieStore()
						.getCookies();
				for (Cookie aCookie : cookies) {
					if (aCookie.getName().equals("dozorSiteSession")) {
						logger.info("Authorization succesfull. dozorSiteSession = "
								+ aCookie.getValue());
						return true;
					}
				}
			}
		}

		return false;
	}
	
}
