package com.iscte.queque.client.main;

import com.iscte.queque.client.gui.ClientFrame;
import com.iscte.queque.client.log.LogMessage;
import com.iscte.queque.client.properties.Properties;

public class ClientMain {
	
	//ATTIBUTES
	private static Properties properties;
	private static LogMessage logger = new LogMessage();

	//MAIN
	public static void main(String[] args) {

		//LOCAL VARIABLES
		String myAccount = "";
		String arg = args[0];
	
		//IF NO ARGS IS GIVEN, USE DEFAULT VALUES
		if (!(args[0].equals(""))) {
			//READ FROM FILE
	    	properties = new Properties(args[0]);
	    	//GET THE STRING WITH DATA
	    	myAccount = properties.getMyAccount();
		} else {
			//READ FROM FILE
	    	properties = new Properties();
	    	//GET THE STRING WITH DATA
	    	myAccount = properties.getMyAccount();
		}
		
    	//INSTANTEATE GUI
		logger.getLog().info("myAccount: " + myAccount);
		new ClientFrame(myAccount).setVisible(true);
	}
}
