package main;

import log.LogMessage;

public class Main {
	
	//LOG4J LOGGER
	private static LogMessage logger = new LogMessage();
	
	//MAIN
	public static void main(String[] args) {
		System.out.println("SERVER-CLIENT APP");
		logger.getLog().info("SERVER-CLIENT APP");
		logger.getLog().debug("SERVER-CLIENT APP");
	}
}
