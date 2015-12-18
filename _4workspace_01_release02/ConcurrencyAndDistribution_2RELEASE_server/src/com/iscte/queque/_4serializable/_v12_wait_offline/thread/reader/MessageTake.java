package com.iscte.queque._4serializable._v12_wait_offline.thread.reader;

import com.iscte.queque._4serializable._v12_wait_offline.interfaces.SharedResource;
import com.iscte.queque._4serializable._v12_wait_offline.message.Message;


public class MessageTake implements Runnable {
	
	//ATTRIBUTE #########################
	private SharedResource shared;
	private Message message;
	private final int SLEEP = 100; //[ms]
	//###################################
	
	//CONSTRUCTOR
	public MessageTake(SharedResource shared, Message message) {
		this.message = message;
		this.shared = shared;
	}
	
	//GETTER
	public SharedResource getShared() {
		return shared;
	}

	public Message getMessage() {
		return message;
	}
		
	//RUN
	@Override
	public void run() {	
		
		//SLEEP
		try {
			Thread.sleep(SLEEP);
			sendToAll(message);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/** WRITE TO ALL: send to all the message */
	private void sendToAll(Message message) {
		//WRITERS
		long time = System.currentTimeMillis();
		
		//SEND TO ALL USERS (consumer)
		try {
			shared.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//TIMER
		time = System.currentTimeMillis() - time;
		System.out.println("[Time = " + time + "]; [ms]");
	}
}
