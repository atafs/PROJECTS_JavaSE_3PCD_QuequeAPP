package com.iscte.queque._4serializable._v12_wait_offline;

import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.iscte.queque._4serializable._v12_wait_offline.listener.chat.BtnOffline;
import com.iscte.queque._4serializable._v12_wait_offline.listener.chat.BtnOnline;
import com.iscte.queque._4serializable._v12_wait_offline.message.Message;


public class ClientMain {
	
	//INSTANTIATE ###########################
	public String ip_v1 = "127.0.0.1";
	public String ip_v2 = "localhost";
	public  int port = 5000;
	private Socket client;
	
	//CONSTANTS
	private final int TOTAL_MESSAGES_AUTOMATIC_TEST = 30;
	
	//STREAM MESSAGE
	private String messageStart;
	
	//STREAM READER/WRITER
	private ObjectOutputStream writer;
	private static String writerKey;
	
	//GUI
	private JFrame frame;
	private String nome;
	private JTextField txtFieldToSend;;
	private JTextArea txtAreaReceived;
	
	private Container panelTextSend;
	private JButton btnSend;
	
	private JPanel panelOnOff;
	private JTextField txtUserName;
	private JButton btnOnline;
	private JButton btnOffline;
	//#######################################

	//MAIN
	public static void main(String[] args) throws Exception  {
		//INSTANTIATE
		ClientMain client = new ClientMain();
//		client.connect_socket();
		
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
		
		// PANEL chatPanel ###########################
		panelOnOff = new JPanel();
		txtUserName = new JTextField();
		btnOnline = new JButton();
		btnOffline = new JButton();
		
		//TEXTFIELD
		Font fonte1 = new Font("Serif", Font.ITALIC, 15);
		txtUserName.setFont(fonte1);
		txtUserName.setText(" " + this.nome + " ");

		// PANEL1
		panelOnOff.setBorder(javax.swing.BorderFactory.createTitledBorder("CONNECTION"));
		btnOnline.setText("ONLINE");
		btnOnline.setBackground(Color.GREEN);
		btnOnline.setForeground(Color.WHITE);
		btnOnline.addActionListener(new BtnOnline(this));

		btnOffline.setText("OFFLINE");
		btnOffline.setEnabled(false);
		btnOffline.addActionListener(new BtnOffline(this));
		
		//BOX
		panelOnOff.setLayout(new BoxLayout(panelOnOff, BoxLayout.X_AXIS));
		
		//ADD
		panelOnOff.add(txtUserName);
		panelOnOff.add(btnOnline);
		panelOnOff.add(btnOffline);
		//#############################################

		//fonte e botoes/texto
		Font fonte = new Font("Serif", Font.ITALIC, 15);
		txtFieldToSend = new JTextField();
		txtFieldToSend.setEnabled(false);
		txtFieldToSend.setFont(fonte);
		txtFieldToSend.addKeyListener(new TextFieldListener());
		btnSend = new JButton("SEND");
		btnSend.setEnabled(false);
		btnSend.setFont(fonte);
		btnSend.addActionListener(new ButtonListener());
		
		//JPanel
		panelTextSend = new JPanel();
		panelTextSend.setLayout(new BorderLayout());
		panelTextSend.add(BorderLayout.CENTER, txtFieldToSend);
		panelTextSend.add(BorderLayout.EAST, btnSend);
		
		//area de recepcao de mensagens
		txtAreaReceived = new JTextArea();
		txtAreaReceived.setFont(fonte);
		txtAreaReceived.setEditable(false);;
		JScrollPane scroll = new JScrollPane(txtAreaReceived);
		
		//container principal
		frame.getContentPane().add(BorderLayout.NORTH, panelOnOff);
		frame.getContentPane().add(BorderLayout.CENTER, scroll);
		frame.getContentPane().add(BorderLayout.SOUTH, panelTextSend);

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
		String s = txtFieldToSend.getText();
		Message message = new Message(nome, s);
		
		//SEND TO SERVER
		for (int i = 0; i < TOTAL_MESSAGES_AUTOMATIC_TEST; i++) {
			try {
				writer.writeObject(message);
				writer.flush();//garantir que foi enviado
				
				//CLEAN TEXTFIELD
				txtFieldToSend.setText("");//limpar campo de texto
				txtFieldToSend.requestFocus();//colocar cursor dentro do campo
				
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
	
	/** GUI: message_sendToServer_online */
	public void message_sendToServer_online() {
		
		//MESSAGE TO SEND FROM TEXTFIELD
		String s = this.nome + " IS NOW ONLINE!!!";
		Message message = new Message(nome, s);
		
		//SEND TO SERVER
		try {
			writer.writeObject(message);
			writer.flush();//garantir que foi enviado
			
			//CLEAN TEXTFIELD
			txtFieldToSend.setText("");//limpar campo de texto
			txtFieldToSend.requestFocus();//colocar cursor dentro do campo
			
			//SLEEP
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** GUI: online_setEnable */
	public void gui_online_setEnable() {
		//VIEW enable ###########################
		txtUserName.setEditable(false);
		
		btnSend.setEnabled(true);
		btnSend.setBackground(Color.GREEN);
		btnSend.setForeground(Color.WHITE);
		
		btnOffline.setBackground(Color.LIGHT_GRAY);
		btnOffline.setForeground(Color.WHITE);
		btnOffline.setEnabled(true);
		
		btnOnline.setBackground(Color.LIGHT_GRAY);
		btnOnline.setForeground(Color.WHITE);
		btnOnline.setEnabled(false);
		
		txtFieldToSend.setEnabled(true);		
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
					//SAVE
					ClientMain.writerKey = message.getWriterKey();				
					
					//adiciona no final de todo o texto o novo texto
					txtAreaReceived.append(message.getFromUser() + " => "+ message.getMessage() + "\n");
				}
			} catch(Exception x) {}
		}		
	}
	
	public void btnOfflineActionPerformed(ActionEvent evt) {
////		this.message.setAction(ChatMessage.Action.DISCONNECT);
//		this.service.send(this.message);
//		disconnected();
	}
	
	public void btnOnlineActionPerformed(ClientMain client, ActionEvent evt) {
//		//USER NAME
//		String name = "";
//		//1-IF textField has a value
//		if (!this.txtName.getText().isEmpty()) {
//			name = this.txtName.getText();
//		}
//		else {
//			//2-IF textField has NO value, but client class has it
//			if (!(myDataClient.getName().equals(""))) {
//				name = myDataClient.getName();
//				this.txtName.setText(name);
//			} 
//			//3-DEFAULT
//			else {
//				name = "defaultNameForUser";
//				this.txtName.setText(name);
//			}
//		}

		//CONNECT (writer)
		client.connect_socket();
		
		//NOTIFICATION: online
		message_sendToServer_online();
		
		//VIEW SET ENABLE
		gui_online_setEnable();
		
	}
	
	//OVERLOAD FOR KeyEvent TO btnEnviarActionPerformed
	public void btnEnviarActionPerformed(KeyEvent e) {
//		aux_enviarListener();
	}
	
	//OVERLOAD FOR KeyEvent TO btnEnviarActionPerformed
	public void btnLimparActionPerformed(KeyEvent e) {
//		aux_enviarListener();
	}
	
	
	
}
