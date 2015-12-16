package com.iscte.queque._4serializable._v10_wait_notifyAll.thread.reader;

import com.iscte.queque._4serializable._v10_wait_notifyAll.interfaces.SharedResource;
import com.iscte.queque._4serializable._v10_wait_notifyAll.message.Message;

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
			shared.put(this);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
