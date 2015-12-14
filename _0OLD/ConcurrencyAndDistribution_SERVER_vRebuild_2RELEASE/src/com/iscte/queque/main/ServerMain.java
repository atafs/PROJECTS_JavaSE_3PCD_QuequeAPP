package com.iscte.queque.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.iscte.queque.db.DBClientData;
import com.iscte.queque.log.LogMessage;

public class ServerMain extends JFrame{
	
	//INSTANTIATE ###########################
	public  int port = 5000;
	public static String ip = "";
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
	
	//CONSTRUCTOR
	public ServerMain() {
		//...
	}
	
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
			logger.getLog().info("SERVER: new reader (ObjectInputStream): constructor");

		}
		
		//RUN
		@Override
		public void run() {		
			//USE THE ObjectInputStream ######################
			try {
				//LOGGER
				logger.getLog().info("LOCATION: type=thread; class=ClientListener_thread; method=run()");
				
				//LOOP
				while((this.texto = (String) this.reader.readObject()) != null) {

					//STRING
					String tempTexto = this.texto;
					String[] tempTextoParts = tempTexto.split("@£§€");
					this.userName = tempTextoParts[0].trim();
					this.userMessage = userName + tempTextoParts[1];
					
					//THREADS
					ExecutorService executor = Executors.newFixedThreadPool(2);
					
					//TODO TO DELETE"""""""""""""""""""""""""""""""""""
					if (DBClientData.writersObjectOutputStream.size() != 0) {
						for (ObjectOutputStream writer1 : DBClientData.writersObjectOutputStream) {
							System.out.println(writer1.toString());
						}
					}
					//""""""""""""""""""""""""""""""""""""""""""""""""""
					
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
			logger.getLog().info("new writer (ObjectOutputStream): constructor");
			
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
				//LOGGER
				logger.getLog().info("LOCATION: name="+nameThread+"; type=thread; class=MessageSave_thread; method=run()");
				
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
			//LOGGER
			logger.getLog().info("LOCATION: name="+nameThread+"; type=thread; class=MessageSend_thread; method=run()");
			
			//SEND OBJECT TO ALL USERS
			sendToAll(this.texto);

		}
		
		/** WRITE TO ALL: send to all the message */
		private void sendToAll(String texto) {
			//WRITERS
			List<ObjectOutputStream> newWritersObjectOutputStream = DBClientData.getAllWriters();
			long time = System.currentTimeMillis();
			try {
				for(int i =0; i < newWritersObjectOutputStream.size(); i++){
					newWritersObjectOutputStream.get(i).writeObject(texto);
					newWritersObjectOutputStream.get(i).flush();	
				}
				//TIMER
				time = System.currentTimeMillis() - time;
				logger.getLog().info("[Time = " + time + "]; [ms]");
	
				} catch (Exception e) {
					logger.getLog().info(e);
				}
			}
	}
}

