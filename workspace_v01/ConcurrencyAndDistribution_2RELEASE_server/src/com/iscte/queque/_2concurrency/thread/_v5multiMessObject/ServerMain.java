package com.iscte.queque._2concurrency.thread._v5multiMessObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.iscte.queque.log.LogMessage;

public class ServerMain {
	
	//INSTANTIATE ###########################
	public  int port = 5000;
	private ServerSocket serverSocket;
	private Socket server;
	
	//STREAM MESSAGE
	private String messageStart;
	
	//STREAM READER/WRITER
	private ObjectOutputStream writer;
	
	//LISTS
	List<PrintWriter> writersPrintWriter = new ArrayList<PrintWriter>();
	List<ObjectOutputStream> writersObjectOutputStream = new ArrayList<ObjectOutputStream>();
	List<String> messages = new ArrayList<String>();

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
				
				//STREAM READER SCANNER THREAD
				new Thread(new ClientListener_thread(server)).start();
				
				//STREAM WRITER PRINTWRITER ##########################
				ObjectOutputStream writer = new ObjectOutputStream(server.getOutputStream());
				writersObjectOutputStream.add(writer);
				logger.getLog().info("new writer (ObjectOutputStream) added to list...");

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
	
	//escutar requisicoes dos clientes
	private class ClientListener_thread implements Runnable {

		//ATTRIBUTES
		private ObjectInputStream reader;
		
		//CONSTRUCTOR
		public ClientListener_thread(Socket socket) throws IOException {
			this.reader = new ObjectInputStream(socket.getInputStream());
		}
		
		//RUN
		@Override
		public void run() {		
			//USE THE ObjectInputStream ######################
			try {
				
				String texto;
				while((texto = (String) this.reader.readObject()) != null) {

					//TODO NEEDS TO BE SHARED RESOURCE
					messages.add(texto);
					
					logger.getLog().info(texto);
					writer_sendToAll_objectoutputstream(texto);
				}
			} catch(Exception e) {
				logger.getLog().debug(e);
			}
			
//			//SYNTAXE: FOR AN OBJECT
//			ChatMessage message = null;
//			(message = (ChatMessage) this.reader.readObject())
//			ChatMessage.Action action = message.getAction();	
		}
	}
		
	/** LIST: send to all the message */
	private void writer_sendToAll_printwriter(String texto) {
		for (PrintWriter w : writersPrintWriter) {
			try {	
				w.println(texto);
				w.flush();
			} catch (Exception e) {
				logger.getLog().info(e);
			}
		}
	}
	
	/** LIST: send to all the message */
	private void writer_sendToAll_objectoutputstream(String texto) {
		for (ObjectOutputStream w : writersObjectOutputStream) {
			try {	
				w.writeObject(texto);
				w.flush();
			} catch (Exception e) {
				logger.getLog().info(e);
			}
		}
	}
}

