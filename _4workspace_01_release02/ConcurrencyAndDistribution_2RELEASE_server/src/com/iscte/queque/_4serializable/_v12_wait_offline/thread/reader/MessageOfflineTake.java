package com.iscte.queque._4serializable._v12_wait_offline.thread.reader;

import com.iscte.queque._4serializable._v12_wait_offline.enums.Offline_Put_Take;
import com.iscte.queque._4serializable._v12_wait_offline.interfaces.SharedResource;
import com.iscte.queque._4serializable._v12_wait_offline.message.Message;


public class MessageOfflineTake implements Runnable {

	//ATTRIBUTE #########################
	private SharedResource shared;
	private Message message;
	private final int SLEEP = 100; //[ms]
	
	//CONSTANT
	private final Offline_Put_Take OFFLINE_TAKE = Offline_Put_Take.TAKE;
	
	//CONSTRUCTOR
	public MessageOfflineTake(SharedResource shared, Message message) {
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

	//RUN
		@Override
		public void run() {	
			
			//SLEEP
			try {
				Thread.sleep(SLEEP);
				sendToAll_afterCheck();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/** WRITE TO ALL: send to all the message */
		private void sendToAll_afterCheck() {
			//WRITERS
			long time = System.currentTimeMillis();
			
			//SEND TO ALL USERS (consumer)
			try {
				shared.offline_put_take(OFFLINE_TAKE, message);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//TIMER
			time = System.currentTimeMillis() - time;
			System.out.println("[Time = " + time + "]; [ms]");
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
