package com.iscte.queque._4serializable._v12_wait_offline.service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.iscte.queque._4serializable._v12_wait_offline.interfaces.SharedResource;
import com.iscte.queque._4serializable._v12_wait_offline.message.Message;
import com.iscte.queque._4serializable._v12_wait_offline.thread._writer.ClientWriter_connect;
import com.iscte.queque._4serializable._v12_wait_offline.thread.reader.ClientReader;

public class ServerService {
	
	//INSTANTIATE ###########################
	public  int port = 5000;
	private ServerSocket serverSocket;
	private Socket server;
	private static int counterServer;
	
	//STREAM MESSAGE
	private String messageStart;
	
	//SHARED RESOURCE
	private SharedResource shared;
	//#######################################
	
	//CONSTRUCTOR
	public ServerService(SharedResource shared) {
		this.shared = shared;
	}
	
	//GETTER
	public SharedResource getShared() {
		return shared;
	}

	/** CONNECT SOCKET */
	public void socket_connect() {
		
		//SERVERSOCKET and SOCKET
		try {
			this.serverSocket = new ServerSocket(port);
			while(true) {
				this.server = serverSocket.accept();
				//RETURN STRING: server info
				message_start();
				
				//RUNNABLE
				Runnable readerServer = new ClientReader(server, this);
				Runnable writerServer = new ClientWriter_connect(server, this);

				//READER
				counterServer++;
				Thread t1 = new Thread(readerServer);
				t1.setName("readerServer" + counterServer);
				t1.start();
				
				//WRITER  
				Thread t2 = new Thread(writerServer);
				t2.setName("writerServer" + counterServer);
				t2.start();

			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
	
	/** CONNECT SOCKET */
	public void offline_writer(Message message) {
		//REMOVE NEW WRITERS
		shared.writer_remove(message.getFromUser());
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
	
//	//INNER CLASS
//	private class Thread_ClientListener_reader implements Runnable {
//
//		//ATTRIBUTES
//		private ObjectInputStream reader;
//		
//		//CONSTRUCTOR
//		public Thread_ClientListener_reader(Socket socket) throws IOException {
//			this.reader = new ObjectInputStream(socket.getInputStream());
//		
//		}
//		
//		//RUN
//		@Override
//		public void run() {		
//			//USE THE ObjectInputStream ######################
//			Message message = null;
//			try {				
//				//LOOP
//				while((message = (Message) this.reader.readObject()) != null) {
//					/* OFFLINE */
//					if (message.getOnOfState().equals(Message.ActionState.OFFLINE)) {
//						
//					}
//					
//					/* ONLINE */
//					//RUNNABLE
//					Runnable messageTake = new MessageTake(shared, message);
//					Runnable messagePut = new MessagePut(shared, message);
//					
//					//THREAD
//					Thread t1 = new Thread(messageTake);//produz
//					t1.setName("messageTakeFrom_" + message.getFromUser());
//					
//					Thread t2 = new Thread(messagePut);//produz
//					t2.setName("messagePutFrom_" + message.getFromUser());
//					
//					//START
//					t1.start();
//					t2.start();
//					
//					//JOIN
//					try {
//						t1.join();
//						t2.join();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
//	//INNER CLASS
//	private class Thread_ClientListener_writer implements Runnable {
//
//		//ATTRIBUTES
//		private ObjectOutputStream writer;
//		private int counterServer;
//				
//		//CONSTRUCTOR
//		public Thread_ClientListener_writer(Socket socket, int counterServer) throws IOException {
//			this.writer = new ObjectOutputStream(server.getOutputStream());
//			this.counterServer = counterServer;
//		}
//		
//		//RUN
//		@Override
//		public void run() {	
//			//ADD NEW WRITERS
//			shared.addNewWriters(writer);
//		}
//	}

	//INNER CLASS
//	public static class DBClientData {
//		
//		/* ATTRIBUTES */
//		//LOCKS
//		private static Lock lockMessages = new ReentrantLock();//create lock
//		private static Lock lockWritersObjectOutputStream = new ReentrantLock();//create lock
//	
//		//LISTS
//		private static ArrayList<Message> messages;
//		private static HashMap<String,ArrayList<Message>> mapMessages;
//		private static List<ObjectOutputStream> writersObjectOutputStream;
//
//		//CONTRUCTOR
//		public DBClientData() {
//			messages = new ArrayList<Message>();
//			mapMessages = new HashMap<String,ArrayList<Message>>();
//			writersObjectOutputStream = new ArrayList<ObjectOutputStream>();
//		}
//		
//		//rotina: subtracting an amount from the account
//		public static void addMessages(MessagePut messagePut){
//			lockMessages.lock();//acquire lock
//			try{
//				
//				// ACTION: PUT elements to the map
//				messages.add(messagePut.getMessage());
//				// SAVE OBJECT
//				DBClientData.mapMessages.put(messagePut.getMessage().getFromUser(), messages);
//	
//				//SLEEP
//				threadSleep(250);
//				
//			} catch(Exception ex) {
//				ex.printStackTrace();
//			} finally {
//				lockMessages.unlock();//release lock	
//			}
//		}
//		
//		//rotina: subtracting an amount from the account
//		public static void addNewWriters(ObjectOutputStream writer){
//			lockWritersObjectOutputStream.lock();//acquire lock
//			try{				
//				//SAVE OBJECT
//				DBClientData.writersObjectOutputStream.add(writer);
//				
//				//SLEEP
//				threadSleep(250);
//				
//			} catch(Exception ex) {
//				ex.printStackTrace();
//			} finally {
//				lockWritersObjectOutputStream.unlock();//release lock
//
//			}
//		}
//		
//		//GET
//		public static void sendAllWriters(Message message){
//			lockWritersObjectOutputStream.lock();//acquire lock
//			try{				
//				//GET ALL
//				for (ObjectOutputStream objectOutputStream : writersObjectOutputStream) {
//					objectOutputStream.writeObject(message);
//					objectOutputStream.flush();
//				}
//				
//				//SLEEP
//				threadSleep(250);
//				
//			} catch(Exception ex) {
//				ex.printStackTrace();
//			} finally {
//				lockWritersObjectOutputStream.unlock();//release lock
//	
//			}
//		}
//		
//		//METHOD SLEEP
//		public static void threadSleep(int milis) {
//			//SLEEP
//			try {
//				Thread.sleep(milis);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}
}

