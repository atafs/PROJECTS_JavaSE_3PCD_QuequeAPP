package com.iscte.queque._4serializable._v10_wait_notifyAll.resource.interface_;

public interface SharedResource {
	
	//classe ponte entre Threads produtoras e consumidoras
	public void put(int valor) throws InterruptedException;
	public int get() throws InterruptedException;

}
