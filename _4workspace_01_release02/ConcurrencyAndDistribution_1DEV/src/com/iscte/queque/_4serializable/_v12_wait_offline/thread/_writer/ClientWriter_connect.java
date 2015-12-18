package com.iscte.queque._4serializable._v12_wait_offline.thread._writer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.iscte.queque._4serializable._v12_wait_offline.service.ServerService;

public class ClientWriter_connect implements Runnable {

	//ATTRIBUTES
	private ObjectOutputStream writer;
	private ServerService serverService;
			
	//CONSTRUCTOR
	public ClientWriter_connect(Socket socket, ServerService serverService) throws IOException {
		this.writer = new ObjectOutputStream(socket.getOutputStream());
		this.serverService = serverService;

	}
	
	//RUN
	@Override
	public void run() {	
		//ADD NEW WRITERS
		String fromUser = "server";
		serverService.getShared().writer_add(fromUser, writer);
	}
}