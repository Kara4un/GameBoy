package ru.daigr.telegram.bot.webhook.request;

import ru.daigr.telegram.bot.data.Update;

//Класс для обработки запросов генерируемых сервером Телеграмма при возникновении Updates для нашего бота

public class BotWebhookRequest {
	
	private boolean isCommand;
	private Update update; 	
	
	public BotWebhookRequest(Update anUpdate) {
		update = anUpdate;
	}
	
}
