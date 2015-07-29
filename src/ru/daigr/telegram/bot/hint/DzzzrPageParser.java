package ru.daigr.telegram.bot.hint;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DzzzrPageParser {
	
	private String needAuthFlag;
	
	DzzzrPageParser(String aNeedAuthFlag){
		needAuthFlag = aNeedAuthFlag;
	}
	
	public boolean needAuth(String dzzzrPageHTML){
		if (dzzzrPageHTML == null) return false;
		if (dzzzrPageHTML.toLowerCase().contains(needAuthFlag)){
			return true;
		}
		return false;
	}
	
	public Hint parseLastHint(String dzzzrPageHTML){
		Document doc = Jsoup.parse(dzzzrPageHTML);
		
		Elements title = doc.select("title");
		Elements aTitle = doc.select(".title");
		
		
		return new Hint();
	}
	
	

}
