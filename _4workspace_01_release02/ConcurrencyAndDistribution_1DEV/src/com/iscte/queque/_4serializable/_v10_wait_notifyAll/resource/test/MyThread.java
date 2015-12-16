package com.iscte.queque._4serializable._v10_wait_notifyAll.resource.test;


public class MyThread extends Thread{

	@SuppressWarnings("unused")
	private static final int NUM = 10000000;
	@SuppressWarnings("unused")
	private MessageGet c;

	public MyThread(MessageGet c) {
		super();
		this.c = c;
	}
	
	@Override
	public void run() {
		//... em desenvolvimento ...
	}
}
