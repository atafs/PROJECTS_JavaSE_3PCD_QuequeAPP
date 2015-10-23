package gui.listener.button;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JTextField;

public class Send implements ActionListener, KeyListener {

	//ATTRIBUTE
	private String nomeClient;
	private JButton send;
	private JTextField textoParaEnviar;
	private PrintWriter escritor;

	//CONSTRUCTOR
	public Send(JButton send, JTextField textoParaEnviar, PrintWriter escritor, String nomeClient) {
		this.send = send;
		this.textoParaEnviar = textoParaEnviar;
		this.escritor = escritor;
		this.nomeClient = nomeClient;
	}
	
	//GETTER
	public JButton getSend() {
		return send;
	}

	//ACTION LISTENER
	@Override
	public void actionPerformed(ActionEvent arg0) {
		writeToScreen();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		//ENTER KEY PRESSED
		if (key == KeyEvent.VK_ENTER) {
			Toolkit.getDefaultToolkit().beep();
			writeToScreen();
		}
	}
	
	//KEYBOARD LISTENER
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	/** */
	private void writeToScreen() {
		escritor.println(nomeClient + " -> " + textoParaEnviar.getText());
		escritor.flush();//garantir que foi enviado
		textoParaEnviar.setText("");//limpar campo de texto
		textoParaEnviar.requestFocus();//colocar cursor dentro do campo
	}
}
