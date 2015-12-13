package com.iscte.queque.log;

import org.apache.log4j.Logger;

public class LogMessage {

	//ATTRIBUTE
	private Logger log;

	//CONSTRUCTOR
	public LogMessage() {
		log = (Logger) org.apache.log4j.Logger.getLogger(LogMessage.class);
	}

	//GETTER
	public Logger getLog() {
		return log;
	}
	
}
