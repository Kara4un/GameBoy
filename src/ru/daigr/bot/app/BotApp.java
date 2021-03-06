package ru.daigr.bot.app;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import ru.daigr.network.http.server.HTTPServer;
import ru.daigr.telegram.bot.Bot;
import ru.daigr.telegram.bot.BotProperties;
import ru.daigr.telegram.bot.MainBotProperties;
import ru.daigr.telegram.bot.PropertiesManager;
import ru.daigr.telegram.bot.blahblah.BlahBot;
import ru.daigr.telegram.bot.data.parse.json.JSONDataParser;
import ru.daigr.telegram.bot.gameboy.GameBoy;
import ru.daigr.telegram.bot.hint.HintBot;
import ru.daigr.telegram.bot.webhook.request.BotWebHookRequestProcessor;

import org.apache.logging.log4j.Logger; 
import org.apache.logging.log4j.LogManager;

public class BotApp {
	
	private static final int FATAL_ERROR = -1;
	
	private HTTPServer webHookServer;
	private BotWebHookRequestProcessor requestProcessor;
	private ArrayList<Bot> bots;
	private PropertiesManager mgr;
	private Logger log4j;
	
	private static String PROPS_FILE_NAME = "config.properties";
	
	public static void main (String[] args) throws Exception {
				
		
		BotApp app = new BotApp();
		app.initLogger();				
		
		app.initBots(app.initPropertiesManager());
		app.initWebHookProcessor();
				
		app.initServer();				
		app.run();
						
	}
	
	public void initBots(PropertiesManager mgr){
		bots = new ArrayList<>();
		Bot gBot = new GameBoy(mgr, log4j);
		Bot bBot = new BlahBot(mgr, log4j);	
		Bot hBot = new HintBot(mgr, log4j);
		bots.add(hBot);
		bots.add(gBot);
		bots.add(bBot);
	}
	
	public void initServer() {
		try {
			webHookServer = new HTTPServer(new InetSocketAddress(Integer.parseInt(mgr.getPropertie(MainBotProperties.TG_PORT))), log4j);
			webHookServer.setRequestProcessor(requestProcessor);
		} catch (IOException e) {
			log4j.error("Could not init webHookServer");
			log4j.error(e); 
			System.exit(FATAL_ERROR);
		}			
	}		
	
	public void initWebHookProcessor(){
		log4j.info("Starting init requestProcessor with requestDataParser. \nRequestProcessor = " + 
			BotWebHookRequestProcessor.class.getName() + "; \nData Parser = " + JSONDataParser.class.getName());
		requestProcessor = new BotWebHookRequestProcessor(new JSONDataParser(), log4j);
		for(Bot aBot : bots){
			requestProcessor.addBot(aBot);
			log4j.info("Adding bot : " + aBot.getClass().getName());
		}
		log4j.info("Web hook processor init finished");
	}
	
	public PropertiesManager initPropertiesManager(){
		
		log4j.info("Starting init propertiesManager. Props file name: " + PROPS_FILE_NAME);		
		mgr = new PropertiesManager(PROPS_FILE_NAME);
		mgr.initProps();
		log4j.info("Succesfully finished. Props file name: " + PROPS_FILE_NAME);
		return mgr;
	}
	
	public void initLogger(){
		log4j = LogManager.getLogger(BotApp.class.getName());
		log4j.info("init Logger finished");
	}
	
	public void run(){		
		try {
			while (true) {
				webHookServer.run();
	            Thread.sleep(100);
	        }
		} catch(Exception e){
			log4j.error("Fatal Error");
			log4j.error(e);
		}
	}

}
