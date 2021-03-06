package com.iscte.queque.client.service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.iscte.queque.client.log.LogMessage;
import com.iscte.queque.client.serializable.ChatMessage;

public class ClientService {
	private Socket socket;
	private ObjectOutputStream output;
	//LOG4J LOGGER
	private static LogMessage logger = new LogMessage();

	public Socket connect() {
		try {
			this.socket = new Socket("localhost", 5555);
			this.output = new ObjectOutputStream(this.socket.getOutputStream());
		} catch (IOException ex) {
			logger.getLog().debug(ex);
		}
		return this.socket;
	}

	public void send(ChatMessage message) {
		try {
			this.output.writeObject(message);
		} catch (IOException ex) {
			logger.getLog().debug(ex);
		}
	}
}