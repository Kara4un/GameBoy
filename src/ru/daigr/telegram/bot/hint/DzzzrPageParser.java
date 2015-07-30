package ru.daigr.telegram.bot.hint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
		
		Hint ret = new Hint();
		
		Elements title = doc.select("title");
		
		if (title == null || title.size() < 1){
			return ret;
		}
		
		ret.setGameName(title.get(0).text());
		
		Elements aTitle = doc.select(".title");
		
		if (aTitle == null || aTitle.size() < 1){
			return ret;
		} else {
			
			Pattern mission = Pattern.compile("Задание \\d+");
			Pattern aHint = Pattern.compile("Подсказка .");
			
			for (Element e : aTitle){
				String data = e.text();							
				
				Matcher mMatcher = mission.matcher(data);
				Matcher hMatcher = aHint.matcher(data);
				
				if (mMatcher.find())
				{
				    ret.setMissionNubmer(mMatcher.group());
				    continue;
				} else if (hMatcher.find()){
					ret.setHintNumber(hMatcher.group());
				}
			}			
		}				
		
		return ret;
	}
	
	

}
