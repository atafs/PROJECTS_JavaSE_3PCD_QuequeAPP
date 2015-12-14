package com.iscte.queque._2concurrency.thread._v7multiMessShareList_moreThreads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.iscte.queque.log.LogMessage;

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
	
	//LOG4J LOGGER
	private static LogMessage logger = new LogMessage();	
	//#######################################
	
	//MAIN
	public static void main(String[] args) throws Exception {
		//INSTANTIATE
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
			logger.getLog().debug(e);
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
		private String texto;

		//USER
		private String userName;
		private String userMessage;
		
		
		//CONSTRUCTOR
		public Thread_ClientListener_reader(Socket socket) throws IOException {
			this.reader = new ObjectInputStream(socket.getInputStream());

		}
		
		//RUN
		@Override
		public void run() {		
			//USE THE ObjectInputStream ######################
			try {				
				//LOOP
				while((this.texto = (String) this.reader.readObject()) != null) {

					//STRING
					String tempTexto = this.texto;
					String[] tempTextoParts = tempTexto.split("@£§€");
					this.userName = tempTextoParts[0].trim();
					this.userMessage = userName + tempTextoParts[1];
					
					//THREADS
					ExecutorService executor = Executors.newFixedThreadPool(2);
					
					//SAVE
					executor.execute(new Thread_MessageSave(this.userName, this.userMessage));
					//SEND
					executor.execute(new Thread_MessageSend(this.userName, this.userMessage));
					//SHUTDOWN
					executor.shutdown();

				}
			} catch(Exception e) {
				logger.getLog().debug(e);
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
		private String texto;
		
		//CONSTRUCTOR
		public Thread_MessageSave(String nameThread, String texto) {
			this.texto = texto;
			this.nameThread = nameThread;
		}
		
		//GETTER
		public String getTexto() {
			return texto;
		}
		
		@Override
		public void run() {	
			try{				
				//ADD OBJECT TO LIST
				DBClientData.addMessages(this);

			} catch (Exception ex) {
				logger.getLog().debug(ex);
			}
		}


	}
	
	//INNER CLASS
	public class Thread_MessageSend implements Runnable {

		//ATTRIBUTE
		private String nameThread;
		private String texto;
		
		//CONSTRUCTOR
		public Thread_MessageSend(String nameThread, String texto) {
			this.texto = texto;
			this.nameThread = nameThread;
		}
			
		//RUN
		@Override
		public void run() {				
			//SEND OBJECT TO ALL USERS
			sendToAll(this.texto);

		}
		
		/** WRITE TO ALL: send to all the message */
		private void sendToAll(String texto) {
			
			//TODO TEST ######################################
			//WRITERS
			List<ObjectOutputStream> newWritersObjectOutputStream = DBClientData.getAllWriters();
			Thread[] t = new Thread[newWritersObjectOutputStream.size()];
			long time = System.currentTimeMillis();
			try {
				for(int i =0; i < newWritersObjectOutputStream.size(); i++){
					//START
					final int INDEX = i;
					t[i]= new Thread() {
					    public void run() {
							try {
								newWritersObjectOutputStream.get(INDEX).writeObject(texto);
								newWritersObjectOutputStream.get(INDEX).flush();	
							} catch (IOException e) {
								logger.getLog().info(e);
							}												    }
					};
					t[i].start();
				}
			
				//JOIN
				for(int j =0; j < newWritersObjectOutputStream.size(); j++){
					try {
						t[j].join();
					} catch (InterruptedException e) {
						logger.getLog().info(e);
					}
				}
				//TIMER
				time = System.currentTimeMillis() - time;
				System.out.println("[Time = " + time + "]; [ms]");
					
					
				} catch (Exception e) {
					logger.getLog().info(e);
				}
			}
	}
	
	//INNER CLASS
	public static class DBClientData {
		
		/* ATTRIBUTES */
		//LOCKS
		private static Lock lockMessages = new ReentrantLock();//create lock
		private static Lock lockWritersObjectOutputStream = new ReentrantLock();//create lock
	
		//LISTS
		private static List<String> messages = new ArrayList<String>();
		private static List<ObjectOutputStream> writersObjectOutputStream = new ArrayList<ObjectOutputStream>();

		
		//rotina: subtracting an amount from the account
		public static void addMessages(Thread_MessageSave messageSaveThread){
			lockMessages.lock();//acquire lock
			try{				
				//SAVE OBJECT
				DBClientData.messages.add(messageSaveThread.getTexto());
				
			} catch(Exception ex) {
				logger.getLog().debug(ex);
			} finally {
				try {
					Thread.sleep(100);
					lockMessages.unlock();//release lock
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		//rotina: subtracting an amount from the account
		public static void addNewWriters(ObjectOutputStream writer){
			lockWritersObjectOutputStream.lock();//acquire lock
			try{				
				//SAVE OBJECT
				DBClientData.writersObjectOutputStream.add(writer);
				
			} catch(Exception ex) {
				logger.getLog().debug(ex);
			} finally {
				try {
					Thread.sleep(100);
					lockWritersObjectOutputStream.unlock();//release lock
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		//GET
		public static List<ObjectOutputStream> getAllWriters(){
			lockWritersObjectOutputStream.lock();//acquire lock
			List<ObjectOutputStream> newWritersObjectOutputStream = new ArrayList<ObjectOutputStream>();
			try{				
				//GET ALL
				for (ObjectOutputStream objectOutputStream : writersObjectOutputStream) {
					newWritersObjectOutputStream.add(objectOutputStream);
				}
				
			} catch(Exception ex) {
				logger.getLog().debug(ex);
			} finally {
				try {
					Thread.sleep(100);
					lockWritersObjectOutputStream.unlock();//release lock
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return newWritersObjectOutputStream;
		}			
	}
}

