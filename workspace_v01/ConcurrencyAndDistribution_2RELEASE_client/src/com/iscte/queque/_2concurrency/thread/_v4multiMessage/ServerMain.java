package com.iscte.queque._2concurrency.thread._v4multiMessage;

import java.io.IOException;
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
	List<PrintWriter> writers = new ArrayList<PrintWriter>();
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
				
				//READER SCANNER THREAD
				new Thread(new ClientListener_thread(server)).start();
				
				//STREAM READER PRINTWRITER
				PrintWriter p = new PrintWriter(server.getOutputStream());
				writers.add(p);
				logger.getLog().info("new writer (printwriter) added to list...");

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
		Scanner leitor;
		
		//CONSTRUCTOR
		public ClientListener_thread(Socket socket) throws IOException {
			leitor = new Scanner(socket.getInputStream());
		}
		
		//RUN
		@Override
		public void run() {
			try {
				String texto;
				while((texto = leitor.nextLine()) != null) {
					//TODO NEEDS TO BE SHARED RESOURCE
					messages.add(texto);
					
					logger.getLog().info(texto);
					writer_sendToAll(texto);
				}
			} catch(Exception e) {}
		}
	}
		
	/** LIST: send to all the message */
	private void writer_sendToAll(String texto) {
		for (PrintWriter w : writers) {
			try {	
				w.println(texto);
				w.flush();
			} catch (Exception e) {
				logger.getLog().info(e);
			}
		}
	}
}

