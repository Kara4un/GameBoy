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
	
	public String toString(){
		return gameName + ";"
				+ missionNubmer + ";"
				+ hintNumber + ";";
	}
	
	public static Hint parseFromString(String src){
		Hint ret = new Hint();
		
		String[] params = src.split(";");
		if (params == null || params.length != 3){
			return ret;
		}
		
		ret.setGameName(params[0]);
		ret.setMissionNubmer(params[1]);
		ret.setHintNumber(params[2]);;
		
		return ret;
	}	
	
	public String printHint(){
		String ret = new String();
		ret+=hintNumber + "\n";
		if (hintText == null || hintText.isEmpty()){
			ret+="Текст подсказки недоступен";
		} else {
			ret+=hintText + "\n";
		}
		
		return ret;
	}

}
