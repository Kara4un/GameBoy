package ru.daigr.telegram.bot.Command;

public enum RawCommand {

	VOID_COMMAND ("", "not implemented"),
	ENTER_CODE("/entercode", "Enters a code to dzzzr engine");
		
	private String desc = "";
	private String command = "";
	
	private RawCommand (String aCommand, String aDesc){
		command = aCommand;
		desc = aDesc;
	}
	
	public static RawCommand getCommand(String aCommand){
		for (RawCommand command : RawCommand.values()){
			if (command.command().equals(aCommand)){
				return command;
			}
		}
		
		return VOID_COMMAND;
	}
	
	private String command() {
		return command;
	}
	
	private String desc() {
		return desc;
	}
}
