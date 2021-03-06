package ru.daigr.telegram.bot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesManager {

	Properties props;
	private String rawFile;
	
	public PropertiesManager (String fileName){
		rawFile = fileName;	
		props = new Properties();
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
		
		FileInputStream inputStream = null; 
		
		try {					
 
			inputStream = new FileInputStream("./" + propFileName); 			
			props.load(inputStream);
 			
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			if (inputStream != null) inputStream.close();
		}		
	}
	
	public String getPropertie(BotProperties propertie){
		return props.getProperty(propertie.getName());
	}
	
	public void setPropertie(BotProperties propertie, String value) {		 		
	 
		OutputStream output = null;		
		try {
	 
			output = new FileOutputStream(rawFile);	 	
			if (props.getProperty(propertie.getName()) == null){
				props.setProperty(propertie.getName(), value);	 
				props.store(output, null);
			} else {
				props.setProperty(propertie.getName(), value);
			}
			props.store(output, "");
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	 
		}
	}
	
}
