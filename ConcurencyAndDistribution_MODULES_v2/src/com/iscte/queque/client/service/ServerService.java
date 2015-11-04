package com.iscte.queque.client.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.iscte.queque.client.dao.Contact;
import com.iscte.queque.client.log.LogMessage;
import com.iscte.queque.client.serializable.ChatMessage;

public class ServerService {
	private ServerSocket serverSocket;
	private Socket socket;
	
	//LISTS
	private Map<String, ObjectOutputStream> mapOnlies = new HashMap<String, ObjectOutputStream>();
	private Map<String, PriorityQueue<ChatMessage>> queueLists = new HashMap<String, PriorityQueue<ChatMessage>>();
	private List<Contact> listContacts = new ArrayList<Contact>();

	//LOG4J LOGGER
	private static LogMessage logger = new LogMessage();

	public ServerService() {
		try {
			this.serverSocket = new ServerSocket(5555);
			System.out.println("SERVIDOR ON.");
			for (;;) {
				this.socket = this.serverSocket.accept();

				//THREAD
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
						boolean isConnected = ServerService.this.connect(message, this.output);

						if (isConnected) {
							ServerService.this.mapOnlies.put(message.getName(), this.output);
							ServerService.this.sendOnlines();
						}
					} else if (action.equals(ChatMessage.Action.SEND_ONE)) {
							ServerService.this.sendOne(message);
					}
				}
			} catch (IOException ex) {
				ServerService.this.disconnected(message, this.output);
				ServerService.this.sendOnlines();
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
			System.out.println("YES. YOU ARE CONNECTED. And YOU ARE THE FIRST ONE");
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
//		sendAll(message);
		System.out.println("USER -> " + message.getName() + " LEFT THE CHATROOM");
	}

	private void send(ChatMessage message, ObjectOutputStream output) {
		try {
			output.writeObject(message);
		} catch (IOException ex) {
			logger.getLog().debug(ex);
		}
	}

	private void sendOne(ChatMessage message) {
		for (Map.Entry<String, ObjectOutputStream> kv : this.mapOnlies.entrySet()) {
			if (((String) kv.getKey()).equals(message.getNameReserved())) {
				try {

					//TODO americo 
					message.setState(ChatMessage.Action.OFFLINE);
					
					if (message.getState().equals(ChatMessage.Action.ONLINE)) {
						((ObjectOutputStream) kv.getValue()).writeObject(message);
					} else if (message.getState().equals(ChatMessage.Action.OFFLINE)) {
						//saveCacheMessage(message, kv);
						//CHECK IF EXISTS
						boolean flagContactExists = false;
						int index = 0;
						for (int i = 0; i < listContacts.size(); i++) {
							if (listContacts.get(i).getName().equals(kv.getKey())) {
								flagContactExists = true;	
								index = i;
							}
						}
						
						//NEW CONTACT
						if(!flagContactExists) {
							int indexLast = listContacts.size()-1;
							if (indexLast == -1) {
								indexLast = 0;
							}
							
							String tempName = message.getNameReserved();
							listContacts.add(new Contact(tempName));
							
							String temMessage = message.getText();
							listContacts.get(indexLast).getMessageQueue().add(temMessage);
							
							int tempCounter = listContacts.get(indexLast).getMessagesToReceive() + 1;
							listContacts.get(indexLast).setMessagesToReceive(tempCounter);

						} 
						
						//EXISTING CONTACT
						else {
							String tempName = message.getNameReserved();
							listContacts.get(index).setName(tempName);
							
							String temMessage = message.getText();
							listContacts.get(index).getMessageQueue().add(temMessage);
							
							int tempCounter = listContacts.get(index).getMessagesToReceive() + 1;
							listContacts.get(index).setMessagesToReceive(tempCounter);
						}
						
						//TODO americo 
						((ObjectOutputStream) kv.getValue()).writeObject(message);

					}
				} catch (IOException ex) {
					logger.getLog().debug(ex);
				}
			}
		}
	}

//	private void sendAll(ChatMessage message) {
//		for (Map.Entry<String, ObjectOutputStream> kv : this.mapOnlies.entrySet()) {
//
//			if (!((String) kv.getKey()).equals(message.getName())) {
//				message.setAction(ChatMessage.Action.SEND_ONE);
//				try {
//					System.out.println("USER -> " + message.getName());
//					((ObjectOutputStream) kv.getValue()).writeObject(message);
//				} catch (IOException ex) {
//					logger.getLog().debug(ex);
//				}
//			}
//		}
//	}

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
	
	/** */
	private void saveCacheMessage(ChatMessage message, Map.Entry<String, ObjectOutputStream> kv) {
		
	}
}
