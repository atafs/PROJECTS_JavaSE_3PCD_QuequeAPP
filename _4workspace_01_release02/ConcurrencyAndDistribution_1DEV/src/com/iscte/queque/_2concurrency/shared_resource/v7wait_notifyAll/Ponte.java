package com.iscte.queque._2concurrency.shared_resource.v7wait_notifyAll;

public interface Ponte {
	
	//classe ponte entre Threads produtoras e consumidoras
	public void set(int valor) throws InterruptedException;
	public int get() throws InterruptedException;

}
