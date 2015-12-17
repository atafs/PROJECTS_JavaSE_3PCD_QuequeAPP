package com.iscte.queque._4serializable._v12_wait_offline.thread._writer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.iscte.queque._4serializable._v12_wait_offline.service.ServerService;

public class ClientWriter_disconnect implements Runnable {

	//ATTRIBUTES
	private ObjectOutputStream writer;
	private ServerService serverService;
			
	//CONSTRUCTOR
	public ClientWriter_disconnect(Socket socket, ServerService serverService) throws IOException {
		this.writer = new ObjectOutputStream(socket.getOutputStream());
		this.serverService = serverService;

	}
	
	//RUN
	@Override
	public void run() {	
		//REMOVE NEW WRITERS
		serverService.getShared().addNewWriters(writer);
	}
}