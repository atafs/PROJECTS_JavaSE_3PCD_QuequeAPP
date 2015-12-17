package com.iscte.queque._4serializable._v12_wait_offline.thread.reader;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.iscte.queque._4serializable._v12_wait_offline.message.Message;
import com.iscte.queque._4serializable._v12_wait_offline.service.ServerService;
import com.iscte.queque._4serializable._v12_wait_offline.thread._writer.ClientWriter_connect;
import com.iscte.queque._4serializable._v12_wait_offline.thread._writer.ClientWriter_disconnect;

public class ClientReader implements Runnable {

	//ATTRIBUTES
	private ServerService serverService;
	private ObjectInputStream reader;
	
	//CONSTRUCTOR
	public ClientReader(Socket socket, ServerService serverService) throws IOException {
		this.reader = new ObjectInputStream(socket.getInputStream());
		this.serverService = serverService;
	
	}
	
	//RUN
	@Override
	public void run() {		
		//USE THE ObjectInputStream ######################
		Message message = null;
		try {				
			//LOOP
			while((message = (Message) this.reader.readObject()) != null) {
				/* OFFLINE */
				if (message.getOnOfState().equals(Message.ActionState.OFFLINE)) {
					//RUNNABLE
					Runnable clientWriter = new ClientWriter_disconnect(serverService, message);

					//THREAD
					Thread t3 = new Thread(clientWriter);//produz
					t3.setName("clientWriter" + message.getFromUser());
					
					//START
					t3.start();				

				} else if (message.getOnOfState().equals(Message.ActionState.ONLINE)) {
					//RUNNABLE
					Runnable messageTake = new MessageTake(serverService.getShared(), message);
					Runnable messagePut = new MessagePut(serverService.getShared(), message);
					
					//THREAD
					Thread t1 = new Thread(messageTake);//produz
					t1.setName("messageTakeFrom_" + message.getFromUser());
					
					Thread t2 = new Thread(messagePut);//produz
					t2.setName("messagePutFrom_" + message.getFromUser());
					
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
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}