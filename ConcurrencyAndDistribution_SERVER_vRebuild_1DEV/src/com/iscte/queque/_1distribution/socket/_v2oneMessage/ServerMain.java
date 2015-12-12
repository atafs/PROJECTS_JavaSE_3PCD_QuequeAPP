package com.iscte.queque._1distribution.socket._v2oneMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import com.iscte.queque.log.LogMessage;

public class ServerMain {
	
	//INSTANTIATE ###########################
	public  int port = 5000;
	private ServerSocket serverSocket;
	private Socket server;
	
	//STREAM MESSAGE
	private String messageStart;
	private String messageServerRead = ":SERVER WRITE => ";
	
	//STREAM READER/WRITER
	private Scanner reader;
	
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
				
				//STREAM READER SCANNER
				this.reader = new Scanner(server.getInputStream());
				logger.getLog().info(this.messageStart);
				logger.getLog().info("\n" + messageServerRead + "\n\t" + reader.nextLine());

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
}

//(porta resposta as requisicoes)
//como o servidor sabe que um cliente chegou...
//ao server, cria um Socket para comunicar
//manipulacao de arquivos: escrever resposta ao utilizador
//encaminha uma mensagem

