package com.iscte.queque._4serializable._v12_wait_offline.interfaces;

import java.io.ObjectOutputStream;

import com.iscte.queque._4serializable._v12_wait_offline.message.Message;

public interface SharedResource {
	
	//classe ponte entre Threads produtoras e consumidoras
	public void online_put(Message message) throws InterruptedException;
	public void online_take() throws InterruptedException;
	public void writer_addNewWriters(ObjectOutputStream writer);

}
