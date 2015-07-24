package ru.daigr.network.http.server;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.text.AbstractDocument.LeafElement;

public class HTTPRequest {
	 
    private final String raw;
    private String method;
    private String location;
    private String version;
    private String body = "";
    private Map<String, String> headers = new HashMap<String, String>();

    public HTTPRequest(String raw) {
        this.raw = raw;
        parse();
    }

    private void parse() {
        // parse the first line
        StringTokenizer tokenizer = new StringTokenizer(raw);
        method = tokenizer.nextToken().toUpperCase();
        location = tokenizer.nextToken();
        version = tokenizer.nextToken();
        // parse the headers
        String[] lines = raw.split("\r\n");
        int empty = 0;
        for (int i = 1; i < lines.length; i++)
        {
        	if (lines[i].isEmpty()) {
        		empty = i;
        		break;        	
        	}
            String[] keyVal = lines[i].split(":", 2);
            headers.put(keyVal[0], keyVal[1]);
                        
        }
        
        //Если предпоследняя строка - пустая, то у запроса есть непустое тело
        if (empty == lines.length - 2){
        	body = lines[lines.length - 1];
        }
        
    }

    public String getMethod() {
        return method;
    }

    public String getLocation() {
        return location;
    }
    
    public String getVersion() {
    	return version;
    }

    public String getHead(String key) {
        return headers.get(key);
    }
    
    public String getBody(){
    	return body;
    }
}