package ru.daigr.telegram.bot.Command;

import ru.daigr.telegram.bot.data.Update;

public class EnterCodeCommand implements Command{
	
	private String toEnter;
	
	public EnterCodeCommand(String aToEnter) {
		toEnter = aToEnter;
	}
	
	public boolean notEmpty(){
		return toEnter == null || toEnter.isEmpty();
	}

	@Override
	public boolean processCommand(Update update) {
		// TODO Auto-generated method stub
		return false;
	}

}
