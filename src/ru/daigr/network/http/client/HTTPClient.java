package ru.daigr.network.http.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.function.Predicate;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
 

public class HTTPClient {
	
	public static Predicate<Object> notNull = (a) -> a != null;
	
	public static void main (String[] arvs){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = null;
		if (arvs != null && arvs.length == 1){
			httpPost = new HttpPost ("http://127.0.0.1:" + arvs[0]);
		} else {
			httpPost = new HttpPost ("http://127.0.0.1:443");
		}
		
		StringEntity params = null;
		
		String str ="{\"update_id\":774044642,\"message\":{\"message_id\":42,\"from\":{\"id\":4451615,\"first_name\":\"Sergey\",\"last_name\":\"Pimenov\",\"username\":\"Kara4un\"},\"chat\":{\"id\":4451615,\"first_name\":\"Sergey\",\"last_name\":\"Pimenov\",\"username\":\"Kara4un\"},\"date\":1437655565,\"photo\":[{\"file_id\":\"AgADAgADoasxGx_tQwABfHuKBQP3ZtuW5FkqAAQMMNsrV_GGqs2ZAAIC\",\"file_size\":1209,\"width\":90,\"height\":59},{\"file_id\":\"AgADAgADoasxGx_tQwABfHuKBQP3ZtuW5FkqAARCr-3GJlKKlcyZAAIC\",\"file_size\":21839,\"width\":320,\"height\":211},{\"file_id\":\"AgADAgADoasxGx_tQwABfHuKBQP3ZtuW5FkqAAQBtiQqGtDU2MuZAAIC\",\"file_size\":49693,\"width\":550,\"height\":363}]}}";	
		try {
			params = new StringEntity(str);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		httpPost.setEntity(params);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (notNull.test(response))
		System.out.println(response.toString());		
	}
 
  
}