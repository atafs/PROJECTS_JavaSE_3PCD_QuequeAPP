package com.iscte.queque._4serializable._v10_wait_notifyAll.thread.reader;

import com.iscte.queque._4serializable._v10_wait_notifyAll.interfaces.SharedResource;
import com.iscte.queque._4serializable._v10_wait_notifyAll.message.Message;

public class MessageSend implements Runnable {
	
	//ATTRIBUTE #########################
	private SharedResource shared;
	private Message message;
	private final int SLEEP = 100; //[ms]
	//###################################
	
	//CONSTRUCTOR
	public MessageSend(SharedResource shared, Message message) {
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
			shared.get(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//TIMER
		time = System.currentTimeMillis() - time;
		System.out.println("[Time = " + time + "]; [ms]");
	}
}
