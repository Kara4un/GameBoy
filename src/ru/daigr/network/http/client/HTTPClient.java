package ru.daigr.network.http.client;

import java.io.IOException;
import java.util.function.Predicate;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
 

public class HTTPClient {
	
	public static Predicate<Object> notNull = (a) -> a != null;
	
	public static void main (String[] arvs){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet ("https://api.telegram.org/bot96007440:AAEIOdGlNtydD9w3DzpJacAnHfQwt8FmKIY/getUpdates");
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (notNull.test(response))
		System.out.println(response.toString());		
	}
 
  
}