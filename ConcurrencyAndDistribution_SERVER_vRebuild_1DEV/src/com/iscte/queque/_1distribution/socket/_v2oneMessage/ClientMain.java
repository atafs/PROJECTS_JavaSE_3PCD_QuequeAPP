package com.iscte.queque._1distribution.socket._v2oneMessage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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
	private String messageClientRead = ":CLIENT READ => ";
	
	//STREAM READER/WRITER
	private Scanner reader;
	private PrintWriter writer;
	
	//GUI
	private JFrame frame;
	private JTextField textoParaEnviar;;
	
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
			
//			//READER SCANNER
//			this.reader = new Scanner(client.getInputStream());
//			logger.getLog().info(this.messageStart);
//			logger.getLog().info(messageClientRead + reader.nextLine());
			
			//WRITER PRINTWRITER
			this.writer = new PrintWriter(client.getOutputStream());

		}  catch (IOException e) {
			logger.getLog().debug(e);
		} finally {
//			reader.close();
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
		frame = new JFrame();
		frame.setTitle("Chat");

		//fonte e botoes/texto
		Font fonte = new Font("Serif", Font.PLAIN, 26);
		textoParaEnviar = new JTextField();
		textoParaEnviar.setFont(fonte);
		JButton botao = new JButton("Enviar");
		botao.setFont(fonte);
		botao.addActionListener(new EnviarListener());
		
		//Container principal e JPanel
		Container envio = new JPanel();
		envio.setLayout(new BorderLayout());
		envio.add(BorderLayout.CENTER, textoParaEnviar);
		envio.add(BorderLayout.EAST, botao);
		frame.getContentPane().add(BorderLayout.SOUTH, envio);

	}
	
	/** GUI: last swing interface */ 
	public void gui_lastInstructions() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,120);
		frame.setLocationRelativeTo(null); 
	}
	
	/** GUI: start swing interface */ 
	public void gui_start() {
		frame.setVisible(true);
	}
	
	/** GUI: inner class listener */ 
	//LISTENER
	private class EnviarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			writer.println(textoParaEnviar.getText());
			writer.flush();//garantir que foi enviado
			textoParaEnviar.setText("");//limpar campo de texto
			textoParaEnviar.requestFocus();//colocar cursor dentro do campo
			
		}	
	}

}

//(IP, porta TCP)
//inserimos uma stream atraves do scanner
