package com.iscte.queque._4serializable._v10_wait_notifyAll.test;


public class Start_CONSUMER_PRODUCER_synchronized_control64 {
	
	public static void main(String[] args) {
		//SHARED RESOURCE
//		SharedResource share = new SharedNotSync();
//		OR
		SharedResource share = new SharedSync();

		//RUNNABLE
		Runnable messagePUT = new MessagePut(share);
		Runnable messageGET = new MessageGet(share);

		//THREAD
		Thread t1 = new Thread(messagePUT);//produz
		t1.setName("messagePUT");
		
		Thread t2 = new Thread(messageGET);//consome
		t2.setName("messageGET");
		
		//START
		t1.start();
		t2.start();
		
		//JOIN
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
