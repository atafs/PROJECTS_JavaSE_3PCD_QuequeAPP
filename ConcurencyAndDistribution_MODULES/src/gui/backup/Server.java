package gui.backup;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
	
	//atributos
	List<PrintWriter> escritores = new ArrayList<PrintWriter>();

	//construtor
	public Server() {
		ServerSocket server;
		try {
			server = new ServerSocket(6002);
			while(true) {
				Socket socket = server.accept();
				new Thread(new EscutaCliente(socket)).start();
				PrintWriter p = new PrintWriter(socket.getOutputStream());
				escritores.add(p);
			}
		} catch (IOException e) {}
	}
	
	//escutar requisicoes dos clientes
	private class EscutaCliente implements Runnable {

		Scanner leitor;
		public EscutaCliente(Socket socket) throws IOException {
			leitor = new Scanner(socket.getInputStream());
		}
		
		@Override
		public void run() {
			try {//capturar exceptions
				String texto;
				while((texto = leitor.nextLine()) != null) {
					System.out.println(texto);
					encaminharParaTodos(texto);
				}
			} catch(Exception e) {}
		}
	}
	
	//encaminhar para todos o texto
	private void encaminharParaTodos(String texto) {
		for (PrintWriter w : escritores) {
			try {	
				w.println(texto);
				w.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//main
	public static void main(String[] args) {
		System.err.println("STARTING THE SERVER...");
		
		//START SERVER
		new Server();
	}
}

/* sysout usados para testes especificos */
//System.out.println("VERIFICACAO: construtor de server chamado");
//System.out.println("VERIFICACAO: entrou no TryCatch");
//System.out.println("VERIFICACAO: inicializou o server");
//System.out.println("VERIFICACAO: estou dentro do while");
//System.out.println("VERIFICACAO: estou no servidor");


