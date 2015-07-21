package ru.daigr.telegram.bot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {

	Properties props;
	private String rawFile;
	
	public PropertiesManager (String fileName){
		rawFile = fileName;		
	}
	
	public boolean initProps() {
		try {
			getPropValues(rawFile);
		} catch (IOException io){
			return false;
		}
		return true;
	}
	
	public String getPropsFileName(){
		return rawFile;
	}
	
	
	public void getPropValues(String propFileName) throws IOException {
		InputStream inputStream = null; 
		try {					
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				props.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 			
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			if (inputStream != null) inputStream.close();
		}		
	}
	
	public String getPropertie(BotProperties propertie){
		return props.getProperty(propertie.getName());
	}
	
}
