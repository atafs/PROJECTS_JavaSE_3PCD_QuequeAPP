package com.iscte.queque._4serializable._v12_wait_offline.thread.reader;

import com.iscte.queque._4serializable._v12_wait_offline.enums.Offline_Put_Take;
import com.iscte.queque._4serializable._v12_wait_offline.interfaces.SharedResource;
import com.iscte.queque._4serializable._v12_wait_offline.message.Message;


public class MessageOfflinePut implements Runnable {

	//ATTRIBUTE #########################
	private SharedResource shared;
	private Message message;
	
	//CONSTANT
	private final Offline_Put_Take OFFLINE_PUT = Offline_Put_Take.PUT;
	
	//CONSTRUCTOR
	public MessageOfflinePut(SharedResource shared, Message message) {
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
			//PUT MESSAGE
			shared.offline_put_take(OFFLINE_PUT, message);
			
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
