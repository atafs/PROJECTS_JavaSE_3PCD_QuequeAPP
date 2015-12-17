package com.iscte.queque._4serializable._v11_wait_notify_sameResource.thread.reader;

import com.iscte.queque._4serializable._v11_wait_notify_sameResource.interfaces.SharedResource;
import com.iscte.queque._4serializable._v11_wait_notify_sameResource.message.Message;

public class MessagePut implements Runnable {

	//ATTRIBUTE #########################
	private SharedResource shared;
	private Message message;
	
	//CONSTRUCTOR
	public MessagePut(SharedResource shared, Message message) {
		this.message = message;
		this.shared = shared;
	}
	
	//GETTER
	public Message getMessage() {
		return message;
	}
	
	public SharedResource getShared() {
		return shared;
	}

	@Override
	public void run() {	
		try{				
			//ADD OBJECT TO LIST
			shared.put(message);
			
			//SLEEP
			threadSleep(250);

		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} 
//		catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}
	
	//METHOD SLEEP
	public static void threadSleep(int milis) {
		//SLEEP
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
