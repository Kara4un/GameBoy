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

import ru.daigr.telegram.bot.data.Update;

public class GameBoy implements Bot{
	
	private String token = "";
	private String hostAddr = "";	
	
	private String user = "";
	private String password = "";
	private String teamLogin = "";
	private String pin = "";
	
	private String dzrHost = "";
	private String dzrPort = "";
	private String dzrMainPath = "";
	private String dzrReferer = "";
	private String dzrUserAgent= "";
	
	private CloseableHttpClient httpClient;
	private HttpHost target;
	private HttpClientContext localContext;
	
	private Logger logger;
	
	public GameBoy(PropertiesManager props, Logger aLogger) {
		logger = aLogger;
		if (props != null){
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
		}		
		
		initClient();
	}	
	
	public boolean processUpdate(Update update){
		try {
			return enterCode("11123dr");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean enterCode(String code) throws UnsupportedEncodingException {
		
		HttpPost httpPost = new HttpPost(dzrMainPath);
        
        httpPost.addHeader("Referer", dzrReferer);
        httpPost.addHeader("User-Agent",dzrUserAgent);
        
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();        
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
        
        for (int i = 0; i < 1; i++) {
            try {
				CloseableHttpResponse response = httpClient.execute(target, httpPost, localContext);
				System.out.print(false);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}               
        }				
		
		
		return false;
	}
	
	private boolean makeAuthirization() throws UnsupportedEncodingException{
		
        AuthCache authCache = new BasicAuthCache();
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(target, basicAuth);

        CookieStore cookieStore = new BasicCookieStore();
        
        localContext.setAuthCache(authCache); 
        localContext.setCookieStore(cookieStore);

        HttpPost httpPost = new HttpPost(dzrMainPath);
                    
        httpPost.addHeader("Referer", dzrReferer);
        httpPost.addHeader("User-Agent",dzrUserAgent);
        
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
            if (response != null){
            	List<Cookie> cookies = localContext.getCookieStore().getCookies(); 
            	for (Cookie aCookie : cookies){
            		if (aCookie.getName().equals("dozorSiteSession")) return true;
            	}
            }            	
        }				
		
		return false;
	}
	
	private void initClient(){
		if (httpClient != null){ return; }
		
		target = new HttpHost(dzrHost, Integer.parseInt(dzrPort), "http");
		HttpClientContext localContext = HttpClientContext.create();
		
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials(teamLogin, pin));        
        httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();
	}		
}
