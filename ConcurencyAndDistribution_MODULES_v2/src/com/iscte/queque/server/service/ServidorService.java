package com.iscte.queque.server.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.iscte.queque.client.list.ManageContact;
import com.iscte.queque.client.list.ManageQueue;
import com.iscte.queque.client.log.LogMessage;
import com.iscte.queque.server.bean.ChatMessage;

public class ServidorService {
	private ServerSocket serverSocket;
	private Socket socket;
	private Map<String, ObjectOutputStream> mapOnlies = new HashMap<String, ObjectOutputStream>();
	private Map<ArrayList<ManageContact>, ManageQueue> queueLists = new HashMap<ArrayList<ManageContact>, ManageQueue>();
	//LOG4J LOGGER
	private static LogMessage logger = new LogMessage();

	public ServidorService() {
		try {
			this.serverSocket = new ServerSocket(5555);
			System.out.println("SERVIDOR ON.");
			for (;;) {
				this.socket = this.serverSocket.accept();

				new Thread(new Out(this.socket)).start();
			}
		} catch (Exception e) {
			logger.getLog().debug(e);

		}
	}

	private class Out implements Runnable {
		private ObjectOutputStream output;
		private ObjectInputStream input;

		public Out(Socket socket) {
			try {
				this.output = new ObjectOutputStream(socket.getOutputStream());
				this.input = new ObjectInputStream(socket.getInputStream());
			} catch (IOException ex) {
				logger.getLog().debug(ex);

			}
		}

		public void run() {
			ChatMessage message = null;
			try {
				while ((message = (ChatMessage) this.input.readObject()) != null) {
					ChatMessage.Action action = message.getAction();

					if (action.equals(ChatMessage.Action.CONNECT)) {
						boolean isConnected = ServidorService.this.connect(message, this.output);

						if (isConnected) {
							ServidorService.this.mapOnlies.put(message.getName(), this.output);
							ServidorService.this.sendOnlines();
						}
					} else {
						if (action.equals(ChatMessage.Action.DISCONNECT)) {
							ServidorService.this.disconnected(message,this.output);
							ServidorService.this.sendOnlines();
							return;
						}
						if (action.equals(ChatMessage.Action.SEND_ONE)) {
							ServidorService.this.sendOne(message);

						} 
//						else if (action.equals(ChatMessage.Action.SEND_ALL)) {
//							ServidorService.this.sendAll(message);
//						}
					}
				}
			} catch (IOException ex) {
				ServidorService.this.disconnected(message, this.output);
				ServidorService.this.sendOnlines();
				System.out.println(message.getName() + " LEFT THE CHAT.");
				logger.getLog().debug(message.getName() + " LEFT THE CHAT.");
			} catch (ClassNotFoundException ex) {
				logger.getLog().debug(ex);

			}
		}
	}

	private boolean connect(ChatMessage message, ObjectOutputStream output) {
		if (this.mapOnlies.size() == 0) {
			message.setText("YES");
			System.out
					.println("YES. YOU ARE CONNECTED. And YOU ARE THE FIRST ONE");
			send(message, output);
			return true;
		}

		Iterator it = this.mapOnlies.entrySet().iterator();
		if (it.hasNext()) {
			Map.Entry<String, ObjectOutputStream> kv = (Map.Entry) it.next();

			if (((String) kv.getKey()).equals(message.getName())) {
				message.setText("NO");
				System.out.println("NO. NOT CONNECTED YET");
				send(message, output);
				return false;
			}
			message.setText("YES");
			System.out.println("YES. YOU ARE CONNECTED.");
			send(message, output);
			return true;
		}

		return false;
	}

	private void disconnected(ChatMessage message, ObjectOutputStream output) {
		this.mapOnlies.remove(message.getName());

		message.setText("LEFT CHATROOM. SEE YOU NEXT TIME!!!");

		message.setAction(ChatMessage.Action.SEND_ONE);
		sendAll(message);
		System.out.println("USER -> " + message.getName()
				+ " LEFT THE CHATROOM");
	}

	private void send(ChatMessage message, ObjectOutputStream output) {
		try {
			output.writeObject(message);
		} catch (IOException ex) {
			logger.getLog().debug(ex);
		}
	}

	private void sendOne(ChatMessage message) {
		for (Map.Entry<String, ObjectOutputStream> kv : this.mapOnlies
				.entrySet()) {
			if (((String) kv.getKey()).equals(message.getNameReserved())) {
				try {
					((ObjectOutputStream) kv.getValue()).writeObject(message);
				} catch (IOException ex) {
					logger.getLog().debug(ex);
				}
			}
		}
	}

	private void sendAll(ChatMessage message) {
		for (Map.Entry<String, ObjectOutputStream> kv : this.mapOnlies.entrySet()) {

			if (!((String) kv.getKey()).equals(message.getName())) {
				message.setAction(ChatMessage.Action.SEND_ONE);
				try {
					System.out.println("USER -> " + message.getName());
					((ObjectOutputStream) kv.getValue()).writeObject(message);
				} catch (IOException ex) {
					logger.getLog().debug(ex);
				}
			}
		}
	}

	private void sendOnlines() {
		Set<String> setNames = new HashSet<String>();
		for (Map.Entry<String, ObjectOutputStream> kv : this.mapOnlies.entrySet()) {
			setNames.add(kv.getKey());
		}

		ChatMessage message = new ChatMessage();
		message.setAction(ChatMessage.Action.USERS_ONLINE);
		message.setSetOnlines(setNames);

		for (Map.Entry<String, ObjectOutputStream> kv : this.mapOnlies.entrySet()) {
			message.setName((String) kv.getKey());
			try {
				((ObjectOutputStream) kv.getValue()).writeObject(message);
			} catch (IOException ex) {
				logger.getLog().debug(ex);
			}
		}
	}
}