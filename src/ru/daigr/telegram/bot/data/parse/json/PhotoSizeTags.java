package ru.daigr.telegram.bot.data.parse.json;

public enum PhotoSizeTags {

	ID("file_id"),
	WIDTH("width"),
	HEIGHT("height"),
	FILE_SIZE("file_size");
	
	private String name; 
	
	private PhotoSizeTags(String aName){
		name = aName;
	}
	
	public String toString() {
		return name;
	}
	
	
}
