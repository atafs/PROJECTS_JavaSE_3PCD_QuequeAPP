package com.iscte.queque._4serializable._v12_wait_offline.thread._writer;

import java.io.IOException;

import com.iscte.queque._4serializable._v12_wait_offline.message.Message;
import com.iscte.queque._4serializable._v12_wait_offline.service.ServerService;

public class ClientWriter_disconnect implements Runnable {

	//ATTRIBUTES
	private Message message;
	private ServerService serverService;
			
	//CONSTRUCTOR
	public ClientWriter_disconnect(ServerService serverService, Message message) throws IOException {
		this.serverService = serverService;
		this.message = message;
	}
	
	//RUN
	@Override
	public void run() {	
		//REMOVE FROM LIST
		serverService.offline_writer(message);

	}

	//GETTERS AND SETTERS
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
	public ServerService getServerService() {
		return serverService;
	}

}