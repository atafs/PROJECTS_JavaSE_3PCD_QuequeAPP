package control_3CD;

import java.net.Socket;
import java.util.Scanner;

public class Start_SERVER_CLIENTE_01_client {
	
	public static void main(String[] args) throws Exception  {
		@SuppressWarnings("resource")
		Socket socket = new Socket("127.0.0.1", 5000);
		Scanner s = new Scanner(socket.getInputStream());
		System.out.println("Mensagem: " + s.nextLine());
		s.close();
	}
}

//(IP, porta TCP)
//inserimos uma stream atraves do scanner
