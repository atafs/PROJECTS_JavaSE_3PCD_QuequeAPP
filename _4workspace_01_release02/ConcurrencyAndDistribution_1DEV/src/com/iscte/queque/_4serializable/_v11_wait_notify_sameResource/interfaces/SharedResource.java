package com.iscte.queque._4serializable._v11_wait_notify_sameResource.interfaces;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.iscte.queque._4serializable._v11_wait_notify_sameResource.message.Message;
import com.iscte.queque._4serializable._v11_wait_notify_sameResource.thread.reader.MessagePut;

public interface SharedResource {
	
	//classe ponte entre Threads produtoras e consumidoras
	public void put(Message message) throws InterruptedException;
	public void take() throws InterruptedException;
	public void addNewWriters(ObjectOutputStream writer);

}
