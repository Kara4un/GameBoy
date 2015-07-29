package ru.daigr.telegram.bot.gameboy;

public enum DzzzrResponseCodes {
	
	DEFAULT(-1, ""),
	BONUS(36, "Принят бонусный код"),
	BONUS_AGAIN(35, "Повторный ввод бонусного кода"),	
	FALSE(40, "Введен ложный код"),
	NOT_ACCEPTED(11, "Код не принят"),
	PRIMARY_YES(8, "Код принят"),
	PRIMARY_AGAIN(7, "Повторный ввод основного кода"),
	CANNOT_AUTHORIZE(-2, "Невозможно авторизоваться"),
	;
	
	private int code;
	private String desc;
	
	private DzzzrResponseCodes(int aCode, String aDesc){
		desc = aDesc;
		code = aCode;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getDesc(){
		return desc;
	}
	
	public boolean equals(DzzzrResponseCodes aCode){
		return code == aCode.getCode() && desc.equals(aCode.getDesc());
		
	}
	
	public static DzzzrResponseCodes find (int code)
	{
		for (DzzzrResponseCodes aCode : DzzzrResponseCodes.values()){
			if (code == aCode.getCode()){
				return aCode;
			}
		}
		return DEFAULT;
	}
}
