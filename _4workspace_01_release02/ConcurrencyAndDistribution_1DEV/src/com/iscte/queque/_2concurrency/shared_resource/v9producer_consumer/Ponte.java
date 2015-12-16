package com.iscte.queque._2concurrency.shared_resource.v9producer_consumer;

public interface Ponte {
	
	//classe ponte entre Threads produtoras e consumidoras
	public void set(int valor) throws InterruptedException;
	public int get() throws InterruptedException;

}
