package com.iscte.queque._4serializable._v10_wait_notifyAll.interfaces;

import java.io.ObjectOutputStream;

import com.iscte.queque._4serializable._v10_wait_notifyAll.message.Message;
import com.iscte.queque._4serializable._v10_wait_notifyAll.thread.reader.MessagePut;

public interface SharedResource {
	
	//classe ponte entre Threads produtoras e consumidoras
	public void put(MessagePut messagePut) throws InterruptedException;
	public void get(Message message) throws InterruptedException;
	public void addNewWriters(ObjectOutputStream writer);

}
