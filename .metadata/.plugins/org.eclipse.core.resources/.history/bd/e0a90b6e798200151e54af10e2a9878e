package com.iscte.queque.client.service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.iscte.queque.server.bean.ChatMessage;

public class ClientService {
	private Socket socket;
	private ObjectOutputStream output;

	public Socket connect() {
		try {
			this.socket = new Socket("localhost", 5555);
			this.output = new ObjectOutputStream(this.socket.getOutputStream());
		} catch (IOException ex) {
			Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return this.socket;
	}

	public void send(ChatMessage message) {
		try {
			this.output.writeObject(message);
		} catch (IOException ex) {
			Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
}