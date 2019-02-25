package utils;

import org.apache.log4j.Logger;

public class Log {
	public static Logger log=Logger.getLogger(Log.class.getName());
	public static void info(String message){
		System.out.println(message);
		log.info(message);
	}
	
	public static void warn(String message){
		System.out.println(message);
		log.warn(message);
	}
	public static void error(String message){
		System.out.println(message);
		log.error(message);
	}
}
