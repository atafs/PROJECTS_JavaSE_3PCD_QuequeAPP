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

		//LOCAL VARIABLE
		String myAccount = "";
	
		//IF NO ARGS IS GIVEN, USE DEFAULT VALUES
		if (!(args.length == 0)) {
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
