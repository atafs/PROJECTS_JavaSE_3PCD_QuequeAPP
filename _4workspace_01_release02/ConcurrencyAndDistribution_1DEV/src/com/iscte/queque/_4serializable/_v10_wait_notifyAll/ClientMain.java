package com.iscte.queque._4serializable._v10_wait_notifyAll;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.iscte.queque._4serializable._v10_wait_notifyAll.message.Message;

public class ClientMain {
	
	//INSTANTIATE ###########################
	public String ip_v1 = "127.0.0.1";
	public String ip_v2 = "localhost";
	public  int port = 5000;
	private Socket client;
	
	//STREAM MESSAGE
	private String messageStart;
	
	//STREAM READER/WRITER
	private ObjectOutputStream writer;
	
	//GUI
	private JFrame frame;
	private String nome;
	private JTextField textoParaEnviar;;
	private JTextArea textoRecebido;
	
	//#######################################

	//MAIN
	public static void main(String[] args) throws Exception  {
		//INSTANTIATE
		ClientMain client = new ClientMain();
		client.connect_socket();
		
		//CLIENT1
		client.gui_initComponents("User");
		
		//MESSAGE
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
			
			//WRITER
			this.writer = new ObjectOutputStream(client.getOutputStream());
			//READER
			new Thread(new Thread_ServerListener_reader(this.client)).start();

		}  catch (IOException e) {
			e.printStackTrace();
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
	public void gui_initComponents(String nome) {
		frame = new JFrame();
		int i = ((int)(Math.random()*100));
		this.nome = nome + i;
//		this.messageClientWrite = his.nome;
		frame.setTitle(this.nome);

		//fonte e botoes/texto
		Font fonte = new Font("Serif", Font.ITALIC, 15);
		textoParaEnviar = new JTextField();
		textoParaEnviar.setFont(fonte);
		textoParaEnviar.addKeyListener(new TextFieldListener());
		JButton botao = new JButton("Enviar");
		botao.setFont(fonte);
		botao.addActionListener(new ButtonListener());
		
		//JPanel
		Container envio = new JPanel();
		envio.setLayout(new BorderLayout());
		envio.add(BorderLayout.CENTER, textoParaEnviar);
		envio.add(BorderLayout.EAST, botao);
		
		//area de recepcao de mensagens
		textoRecebido = new JTextArea();
		textoRecebido.setFont(fonte);
		textoRecebido.setEditable(false);;
		JScrollPane scroll = new JScrollPane(textoRecebido);
		
		//container principal
		frame.getContentPane().add(BorderLayout.CENTER, scroll);
		frame.getContentPane().add(BorderLayout.SOUTH, envio);

	}
	
	/** GUI: last swing interface */ 
	public void gui_lastInstructions() {
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(500,250);
		this.frame.setLocationRelativeTo(null); 
	}
	
	/** GUI: start swing interface */ 
	public void gui_start() {
		this.frame.setVisible(true);
	}
	
	/** GUI: action for action and key listener */
	public void message_sendToServer() {
		
		//MESSAGE TO SEND FROM TEXTFIELD
		String s = textoParaEnviar.getText();
		Message message = new Message(nome, s);
		
		final int MAX = 1;
		for (int i = 0; i < MAX; i++) {
			try {
				writer.writeObject(message);
				writer.flush();//garantir que foi enviado
				
				//CLEAN TEXTFIELD
				textoParaEnviar.setText("");//limpar campo de texto
				textoParaEnviar.requestFocus();//colocar cursor dentro do campo
				
				//SLEEP
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** GUI: inner class listener */ 
	//LISTENER
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Thread t = new Thread() {
			    public void run() {
			    	//Thread t1 = Thread.currentThread();
			    	this.setName(nome);
					message_sendToServer();
			    }
			};
			t.start();
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
				message_sendToServer();
			}
			
			//DELETE KEY PRESSED
			if (key == KeyEvent.VK_DELETE) {
				Toolkit.getDefaultToolkit().beep();
				message_sendToServer();
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {}	
		
		@Override
		public void keyTyped(KeyEvent e) {}
	}
	
	//class de escuta do servidor
	private class Thread_ServerListener_reader implements Runnable {
		
		//ATTRIBUTES
		private ObjectInputStream reader;
				
		//CONSTRUCTOR
		public Thread_ServerListener_reader(Socket socket) throws IOException {
			this.reader = new ObjectInputStream(socket.getInputStream());
		}
		
		//RUN
		@Override
		public void run() {
			Message message = null;
			try {
				while((message = (Message) reader.readObject()) != null) {
					//adiciona no final de todo o texto o novo texto
					textoRecebido.append(message.getFromUser() + " => "+ message.getMessage() + "\n");
				}
			} catch(Exception x) {}
		}		
	}
}