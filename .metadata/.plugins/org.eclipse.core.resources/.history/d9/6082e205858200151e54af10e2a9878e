package com.iscte.queque.client.thread;

import java.io.ObjectInputStream;
import java.net.Socket;

import com.iscte.queque.client.gui.ClientFrame;
import com.iscte.queque.client.log.LogMessage;
import com.iscte.queque.server.bean.ChatMessage;

/** THREAD to receive messages from CLIENT */
public class In implements Runnable {
	
	//ATTRIBUTES
	private ObjectInputStream input;
	private ClientFrame gui;
	private Socket socket;
	//LOG4J LOGGER
	private static LogMessage logger = new LogMessage();

	public In(ClientFrame gui, Socket socket) {
		this.gui = gui;
		this.socket = socket;
		try {
			this.input = new ObjectInputStream(socket.getInputStream());
		} catch (java.io.IOException ex) {
			logger.getLog().info("IOException="+ex);
		}
	}

	public void run() {
		ChatMessage message = null;
		try {
			while ((message = (ChatMessage) this.input.readObject()) != null) {
				ChatMessage.Action action = message.getAction();

				if (action.equals(ChatMessage.Action.CONNECT)) {
					gui.connect(message);
				} else if (action.equals(ChatMessage.Action.DISCONNECT)) {
					gui.disconnected();
					gui.getSocket().close();
				} else if (action.equals(ChatMessage.Action.SEND_ONE)) {
					gui.receive(message);
				} else if (action.equals(ChatMessage.Action.USERS_ONLINE)) {
					gui.refreshOnlines(message);
				}
			}
		} catch (java.io.IOException ex) {
			logger.getLog().info("IOException="+ex);
		} catch (ClassNotFoundException ex) {
			logger.getLog().info("ClassNotFoundException="+ex);
		}
	}
}
