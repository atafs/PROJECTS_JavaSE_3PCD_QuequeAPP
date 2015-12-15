package com.iscte.queque._4serializable._v8_serializableMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.iscte.queque._4serializable._v8_serializableMessage.message.Message;


public class ServerMain {
	
	//INSTANTIATE ###########################
	public  int port = 5000;
	private ServerSocket serverSocket;
	private Socket server;
	
	//STREAM MESSAGE
	private String messageStart;
	
	//STREAM READER/WRITER
	@SuppressWarnings("unused")
	private ObjectOutputStream writer;
	
	//#######################################
	
	//MAIN
	public static void main(String[] args) throws Exception {
		//INSTANTIATE
		new DBClientData();
		
		ServerMain server = new ServerMain();
		server.connect_socket();
		
	}
	
	/** CONNECT SOCKET */
	public void connect_socket() {
		
		//SERVERSOCKET and SOCKET
		try {
			this.serverSocket = new ServerSocket(port);
			while(true) {
				this.server = serverSocket.accept();
				//RETURN STRING: server info
				message_start();
				
				//READER 
				new Thread(new Thread_ClientListener_reader(server)).start();
				//WRITER  
				new Thread(new Thread_ClientListener_writer(server)).start();

			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
	
	/** Message: start of server */
	private String message_start() {
		//STRING
		this.messageStart = "SERVER ONLINE " + "\n";
		this.messageStart += "\tserver.Port = " + this.server.getPort() + "\n";
		this.messageStart += "\tserver.LocalPort = " + this.server.getLocalPort() + "\n";
		this.messageStart += "\tserver.LocalAddress = " + this.server.getLocalAddress() + "\n";
		this.messageStart += "\tserver.HostName = " + this.server.getInetAddress().getHostName() + "\n";
		this.messageStart += "\tserver.HostAddress = " + this.server.getInetAddress().getHostAddress();
		//RETURN
		return this.messageStart;
	}
	
	//INNER CLASS
	private class Thread_ClientListener_reader implements Runnable {

		//ATTRIBUTES
		private ObjectInputStream reader;
		
		//CONSTRUCTOR
		public Thread_ClientListener_reader(Socket socket) throws IOException {
			this.reader = new ObjectInputStream(socket.getInputStream());
		
		}
		
		//RUN
		@Override
		public void run() {		
			//USE THE ObjectInputStream ######################
			Message message = null;
			try {				
				//LOOP
				while((message = (Message) this.reader.readObject()) != null) {
					//THREADS
					ExecutorService executor = Executors.newFixedThreadPool(2);
					
					//THREAD NAME
					String threadMessageSaveName = "threadSave_" + message.getFromUser();
					String threadMessageSendName = "threadSend_" + message.getFromUser();
					
					//SAVE
					executor.execute(new Thread_MessageSave(threadMessageSaveName, message));
					//SEND
					executor.execute(new Thread_MessageSend(threadMessageSendName, message));
					
					//CLEAN
					message = null;
					
					//SHUTDOWN
					executor.shutdown();

				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//INNER CLASS
	private class Thread_ClientListener_writer implements Runnable {

		//ATTRIBUTES
		private ObjectOutputStream writer;
				
		//CONSTRUCTOR
		public Thread_ClientListener_writer(Socket socket) throws IOException {
			this.writer = new ObjectOutputStream(server.getOutputStream());
			
		}
		
		//RUN
		@Override
		public void run() {	
			//ADD NEW WRITERS
			DBClientData.addNewWriters(writer);
		}
	}

	//INNER CLASS
	public class Thread_MessageSave implements Runnable {

		//ATTRIBUTE
		private String nameThread;
		private Message message;
		
		//CONSTRUCTOR
		public Thread_MessageSave(String nameThread, Message message) {
			this.message = message;
			this.nameThread = nameThread;
		}
		
		//GETTER
		public Message getMessage() {
			return message;
		}
		
		@Override
		public void run() {	
			try{				
				//ADD OBJECT TO LIST
				DBClientData.addMessages(this);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}


	}
	
	//INNER CLASS
	public class Thread_MessageSend implements Runnable {

		//ATTRIBUTE
		private String nameThread;
		private Message message;
		
		//CONSTRUCTOR
		public Thread_MessageSend(String nameThread, Message message) {
			this.message = message;
			this.nameThread = nameThread;
		}
			
		//RUN
		@Override
		public void run() {				
			//SEND OBJECT TO ALL USERS
			sendToAll(message);

		}
		
	/** WRITE TO ALL: send to all the message */
	private void sendToAll(Message message) {
		
		//TODO TEST ######################################
		//WRITERS
		long time = System.currentTimeMillis();
		DBClientData.sendAllWriters(message);
		
		//TIMER
		time = System.currentTimeMillis() - time;
		System.out.println("[Time = " + time + "]; [ms]");
		}
	}
	
	//INNER CLASS
	public static class DBClientData {
		
		/* ATTRIBUTES */
		//LOCKS
		private static Lock lockMessages = new ReentrantLock();//create lock
		private static Lock lockWritersObjectOutputStream = new ReentrantLock();//create lock
	
		//LISTS
		private static ArrayList<Message> messages;
		private static HashMap<String,ArrayList<Message>> mapMessages;
		private static List<ObjectOutputStream> writersObjectOutputStream;

		//CONTRUCTOR
		public DBClientData() {
			messages = new ArrayList<Message>();
			mapMessages = new HashMap<String,ArrayList<Message>>();
			writersObjectOutputStream = new ArrayList<ObjectOutputStream>();
		}
		
		//rotina: subtracting an amount from the account
		public static void addMessages(Thread_MessageSave messageSaveThread){
			lockMessages.lock();//acquire lock
			try{
				
				// ACTION: PUT elements to the map
				messages.add(messageSaveThread.getMessage());
				// SAVE OBJECT
				DBClientData.mapMessages.put(messageSaveThread.getMessage().getFromUser(), messages);
	
				//SLEEP
				threadSleep(250);
				
			} catch(Exception ex) {
				ex.printStackTrace();
			} finally {
				lockMessages.unlock();//release lock	
			}
		}
		
		//rotina: subtracting an amount from the account
		public static void addNewWriters(ObjectOutputStream writer){
			lockWritersObjectOutputStream.lock();//acquire lock
			try{				
				//SAVE OBJECT
				DBClientData.writersObjectOutputStream.add(writer);
				
				//SLEEP
				threadSleep(250);
				
			} catch(Exception ex) {
				ex.printStackTrace();
			} finally {
				lockWritersObjectOutputStream.unlock();//release lock

			}
		}
		
		//GET
		public static void sendAllWriters(Message message){
			lockWritersObjectOutputStream.lock();//acquire lock
			try{				
				//GET ALL
				for (ObjectOutputStream objectOutputStream : writersObjectOutputStream) {
					objectOutputStream.writeObject(message);
					objectOutputStream.flush();
				}
				
				//SLEEP
				threadSleep(250);
				
			} catch(Exception ex) {
				ex.printStackTrace();
			} finally {
				lockWritersObjectOutputStream.unlock();//release lock
	
			}
		}			
	}
	
	//METHOD SLEEP
	public static void threadSleep(int milis) {
		//SLEEP
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

