package com.iscte.queque._4serializable._v12_wait_offline.thread.reader;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.iscte.queque._4serializable._v12_wait_offline.message.Message;
import com.iscte.queque._4serializable._v12_wait_offline.service.ServerService;

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
//					//RUNNABLE
//					Runnable messageOfflinePut = new MessageOfflinePut(serverService.getShared(), message);
//
//					//THREAD
//					Thread t3 = new Thread(messageOfflinePut);//produz
//					t3.setName("messageOfflinePut_" + message.getFromUser());
//					
//					//START
//					t3.start();	
					
					//TODO TEST
					//RUNNABLE
					Runnable messageofflineTake = new MessageOfflineTake(serverService.getShared(), message);
					Runnable messageOnlineTake = new MessageOnlineTake(serverService.getShared(), message);
					Runnable messageOnlinePut = new MessageOnlinePut(serverService.getShared(), message);
					
					//THREAD
					Thread t0 = new Thread(messageofflineTake);//produz
					t0.setName("messageofflineTake_" + message.getFromUser());
					
					Thread t1 = new Thread(messageOnlineTake);//produz
					t1.setName("messageOnlineTake_" + message.getFromUser());
					
					Thread t2 = new Thread(messageOnlinePut);//produz
					t2.setName("messageOnlinePut_" + message.getFromUser());
					
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
					
//					//START
//					t0.start();

				} else if (message.getOnOfState().equals(Message.ActionState.ONLINE)) {
					//RUNNABLE
					Runnable messageofflineTake = new MessageOfflineTake(serverService.getShared(), message);
					Runnable messageOnlineTake = new MessageOnlineTake(serverService.getShared(), message);
					Runnable messageOnlinePut = new MessageOnlinePut(serverService.getShared(), message);
					
					//THREAD
					Thread t0 = new Thread(messageofflineTake);//produz
					t0.setName("messageofflineTake_" + message.getFromUser());
					
					Thread t1 = new Thread(messageOnlineTake);//produz
					t1.setName("messageOnlineTake_" + message.getFromUser());
					
					Thread t2 = new Thread(messageOnlinePut);//produz
					t2.setName("messageOnlinePut_" + message.getFromUser());
					
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
					
//					//START
//					t0.start();
				}			
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}