package com.iscte.queque._4serializable._v12_wait_offline.interfaces;

import java.io.ObjectOutputStream;

import com.iscte.queque._4serializable._v12_wait_offline.enums.Offline_Put_Take;
import com.iscte.queque._4serializable._v12_wait_offline.message.Message;

public interface SharedResource {
	
	//ONLINE
	public void online_put(Message message) throws InterruptedException;
	public void online_take() throws InterruptedException;
	public void writer_addNewWriters(ObjectOutputStream writer);
	
	//OFFLINE
	public void offline_put_take(Offline_Put_Take OFFLINE_TAKE, Message message) throws InterruptedException;

}
