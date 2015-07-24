package ru.daigr.telegram.bot.data;

public class PhotoSize {
	
	private String fileId;
	private long width;	
	private long height;
	private long file_size;
		
	public PhotoSize(String aFileId){
		fileId = aFileId;
			
	}
	
	public long getWidth() {
		return width;
	}

	public void setWidth(long width) {
		this.width = width;
	}

	public long getHeight() {
		return height;
	}

	public void setHeight(long height) {
		this.height = height;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileId(){
		return fileId;
	}
		
	public long getFile_size() {
		return file_size;
	}

	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}

	public static PhotoSize defultUpdate(){
		return new PhotoSize("");
	}		

}
