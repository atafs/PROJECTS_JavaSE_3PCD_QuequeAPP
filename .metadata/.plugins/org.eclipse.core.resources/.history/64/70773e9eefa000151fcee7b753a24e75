package control_3CD;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Start_SERVER_CLIENTE_01_server {
	
	public static void main(String[] args) throws Exception {
		
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(5000);
		while(true) {
			Socket socket = server.accept();
			try ( PrintWriter w = new PrintWriter(socket.getOutputStream()) ) {
				w.println("Aprenda Java e seja contratado!!!");
			}
		}
	}
}

//(porta resposta as requisicoes)
//como o servidor sabe que um cliente chegou...
//ao server, cria um Socket para comunicar
//manipulacao de arquivos: escrever resposta ao utilizador
//encaminha uma mensagem

