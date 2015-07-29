package ru.daigr.telegram.bot.hint;

public class Hint {
	
	private String gameName;
	private String missionNubmer;
	private String hintNumber;
	private String hintText;
	
	public Hint(){
		
	}

	public String getHintNumber() {
		return hintNumber;
	}

	public void setHintNumber(String hintNumber) {
		this.hintNumber = hintNumber;
	}

	public String getMissionNubmer() {
		return missionNubmer;
	}

	public void setMissionNubmer(String missionNubmer) {
		this.missionNubmer = missionNubmer;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getHintText() {
		return hintText;
	}

	public void setHintText(String hintText) {
		this.hintText = hintText;
	}	
	
	public boolean equals(Hint aHint){
		return aHint!=null && aHint.getGameName().equals(gameName)
				&& aHint.getHintNumber().equals(hintNumber)
				&& aHint.getMissionNubmer().equals(missionNubmer);
				
	}

}
