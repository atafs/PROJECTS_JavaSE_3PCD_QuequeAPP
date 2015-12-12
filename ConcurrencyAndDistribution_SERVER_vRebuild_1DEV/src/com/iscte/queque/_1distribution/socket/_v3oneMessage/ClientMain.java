package com.iscte.queque._1distribution.socket._v3oneMessage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.iscte.queque.log.LogMessage;

public class ClientMain {
	
	//INSTANTIATE ###########################
	public String ip_v1 = "127.0.0.1";
	public String ip_v2 = "localhost";
	public  int port = 5000;
	private Socket client;
	
	//STREAM MESSAGE
	private String messageStart;
	private String messageClientWrite= ":CLIENT WRITE => message = ";
	
	//STREAM READER/WRITER
	private PrintWriter writer;
	
	//FLAGS
	private boolean flagFirstMessage = true;

	//GUI
	private JFrame frame;
	private Container envio;
	private JTextField textoParaEnviar;;
	private JButton botao;
	
	//LOG4J LOGGER
	private static LogMessage logger = new LogMessage();
	//#######################################

	//MAIN
	public static void main(String[] args) throws Exception  {
		//INSTANTIATE
		ClientMain client = new ClientMain();
		client.connect_socket();
		client.gui_initComponents();
		client.gui_lastInstructions();
		client.gui_start();

	}
	
	/** CONNECT SOCKET */
	public void connect_socket() {
		
		//SOCKET
		try {
			this.client = new Socket(ip_v1, port);
			//RETURN STRING: server info
			message_start();
			
			//WRITER PRINTWRITER
			this.writer = new PrintWriter(client.getOutputStream());

		}  catch (IOException e) {
			logger.getLog().debug(e);
		} finally {
			//...
		}
	}
	
	/** Message: start of server */
	private String message_start() {
		//STRING
		this.messageStart = "CLIENT ONLINE " + "\n";
		this.messageStart += "\tclient.Port = " + this.client.getPort() + "\n";
		this.messageStart += "\tclient.LocalPort = " + this.client.getLocalPort() + "\n";
		this.messageStart += "\tclient.LocalAddress = " + this.client.getLocalAddress() + "\n";
		this.messageStart += "\tclient.HostName = " + this.client.getInetAddress().getHostName() + "\n";
		this.messageStart += "\tclient.HostAddress = " + this.client.getInetAddress().getHostAddress();
		//RETURN
		return this.messageStart;
	}
	
	/** GUI: init swing interface */ 
	public void gui_initComponents() {
		this.frame = new JFrame();
		this.frame.setTitle("Chat");

		//fonte e botoes/texto
		Font fonte = new Font("Serif", Font.PLAIN, 26);
		this.textoParaEnviar = new JTextField();
		this.textoParaEnviar.setFont(fonte);
		this.textoParaEnviar.addKeyListener(new TextFieldListener());
		botao = new JButton("Enviar");
		this.botao.setFont(fonte);
		this.botao.addActionListener(new ButtonListener());
		
		//Container principal e JPanel
		this.envio = new JPanel();
		this.envio.setLayout(new BorderLayout());
		this.envio.add(BorderLayout.CENTER, textoParaEnviar);
		this.envio.add(BorderLayout.EAST, botao);
		this.frame.getContentPane().add(BorderLayout.SOUTH, envio);

	}
	
	/** GUI: last swing interface */ 
	public void gui_lastInstructions() {
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(500,120);
		this.frame.setLocationRelativeTo(null); 
	}
	
	/** GUI: start swing interface */ 
	public void gui_start() {
		this.frame.setVisible(true);
	}
	
	/** GUI: action for action and key listener */
	public void action_buttonOrEnterPressed() {
		//LOCAL VARIABLE
		String messageToSend = messageClientWrite + textoParaEnviar.getText();
		
		//WRITER
		writer.println(messageToSend);
		writer.flush();//garantir que foi enviado
		textoParaEnviar.setText("");//limpar campo de texto
		textoParaEnviar.requestFocus();//colocar cursor dentro do campo
		
		//TO PRINT ONLY FIRST MESSAGE
		if (flagFirstMessage) {
			ClientMain.logger.getLog().info(messageToSend);
			flagFirstMessage = false;
		} else {
			ClientMain.logger.getLog().info("ALREADY SENT ONE MESSAGE. NO LOOP INSTALLED IN APP!!");

		}
	}
	
	/** GUI: inner class listener */ 
	//LISTENER
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			action_buttonOrEnterPressed();
		}
	}
	
	//LISTENER
	private class TextFieldListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			//ENTER KEY PRESSED
			if (key == KeyEvent.VK_ENTER) {
				Toolkit.getDefaultToolkit().beep();
				action_buttonOrEnterPressed();
			}
			
			//DELETE KEY PRESSED
			if (key == KeyEvent.VK_DELETE) {
				Toolkit.getDefaultToolkit().beep();
				action_buttonOrEnterPressed();
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {}	
		
		@Override
		public void keyTyped(KeyEvent e) {}
	}
}

//(IP, porta TCP)
//inserimos uma stream atraves do scanner
