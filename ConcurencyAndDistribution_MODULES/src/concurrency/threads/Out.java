package concurrency.threads;

import java.util.Scanner;

import javax.swing.JTextArea;

public class Out implements Runnable{
	
	//ATTIBUTES
	private JTextArea textoRecebido;
	private Scanner leitor;
	
	@Override
	public void run() {
		try {
			String texto;
			while((texto = leitor.nextLine()) != null) {
				//adiciona no final de todo o texto o novo texto
				textoRecebido.append(texto + "\n");
			}
		} catch(Exception x) {}
	}

}
