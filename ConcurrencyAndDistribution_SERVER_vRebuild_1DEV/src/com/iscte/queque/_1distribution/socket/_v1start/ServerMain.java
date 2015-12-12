package com.iscte.queque._1distribution.socket._v1start;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.iscte.queque.log.LogMessage;

public class ServerMain {
	
	//INSTANTIATE ###########################
	public  int port = 5000;
	private ServerSocket serverSocket;
	private Socket server;
	
	//STREAM
	private String message;
	private String messageStart;
	
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
				messageStart();

				try ( PrintWriter w = new PrintWriter(this.server.getOutputStream()) ) {
					this.message = "Aprenda Java e seja contratado!!!";
					w.println(this.message);
					logger.getLog().info(this.messageStart);
					logger.getLog().info("Mensagem: " + this.message);
				
				}
			}
		} catch (IOException e) {
			logger.getLog().debug(e);
		}
	}
	
	
	/** Message: start of server */
	private String messageStart() {
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

