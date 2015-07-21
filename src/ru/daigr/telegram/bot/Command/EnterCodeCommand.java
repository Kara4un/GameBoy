package ru.daigr.telegram.bot.Command;

import ru.daigr.telegram.bot.data.Message;

public class EnterCodeCommand implements Command{
	
	private String toEnter;
	
	public EnterCodeCommand(String aToEnter) {
		toEnter = aToEnter;
	}
	
	public boolean notEmpty(){
		return toEnter == null || toEnter.isEmpty();
	}

	@Override
	public boolean processCommand(Message message) {
		// TODO Auto-generated method stub
		return false;
	}

}
