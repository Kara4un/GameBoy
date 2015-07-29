package ru.daigr.network.http.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.function.Predicate;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
 

public class HTTPClient {
	
	public static Predicate<Object> notNull = (a) -> a != null;
	
	public static void main (String[] arvs){						
		for (int i = 0; i < 1; i++){
			Runnable r = new R();
			(new Thread(r)).start();
		}				
	}
	
	public static class R implements Runnable{

		@Override
		public void run() {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = null;
			//if (arvs != null && arvs.length == 1){
				//httpPost = new HttpPost ("http://127.0.0.1:" + arvs[0]);
			//} else {
				httpPost = new HttpPost ("http://127.0.0.1:443");
	//		}
			
			StringEntity params = null;
			
			String str ="{\"update_id\":774044658,\"message\":{\"message_id\":76,\"from\":{\"id\":4451615,\"first_name\":\"Sergey\",\"last_name\":\"Pimenov\",\"username\":\"Kara4un\"},\"chat\":{\"id\":-38364777,\"title\":\"gameboy_t\"},\"date\":1438085018,\"text\":\"\\/get_hint 123123123\"}}";	
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
 
  
}