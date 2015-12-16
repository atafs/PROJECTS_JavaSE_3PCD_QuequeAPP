package com.iscte.queque._4serializable._v10_wait_notifyAll.thread;

import com.iscte.queque._4serializable._v10_wait_notifyAll.message.Message;
import com.iscte.queque._4serializable._v10_wait_notifyAll.resource.interface_.SharedResource;
import com.iscte.queque._4serializable._v10_wait_notifyAll.service.ServerService.DBClientData;

public class MessageSend implements Runnable {
	
	//ATTRIBUTE #########################
	private SharedResource shared;
	private Message message;
	private final int SLEEP = 4000; //[ms]
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
		
		//TODO TEST ######################################
		//WRITERS
		long time = System.currentTimeMillis();
		DBClientData.sendAllWriters(message);
		
		//TIMER
		time = System.currentTimeMillis() - time;
		System.out.println("[Time = " + time + "]; [ms]");
	}
}
