package pt.progJava.americoLib.control107.gui;

import distribution.serializable.ChatMessage;
import distribution.service.ClienteService;
import gui.client.backup.ClienteFrame;
import gui.listener.Connect;
import gui.listener.Enviar;
import gui.listener.Exit;
import gui.listener.Send;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import concurrency.threads.Out;
import log.LogMessage;
 
public class Client extends JFrame {
     
	//ATTRIBUTES
	private static final long serialVersionUID = 1L;
	//LOG4J LOGGER
	private static LogMessage logger = new LogMessage();
	
	private String nomeClient;
	private ImageIcon image;
	private JTextField textoParaEnviar;
	private Socket socket;
	private PrintWriter escritor;
	private Scanner leitor;
	
	private DefaultListModel<Object> listModel;
	private JList<Object> countryList;
	private JScrollPane scroll;
	
	//TODO INTEGRATE NEW FEATURES
	private ChatMessage message;
	private JButton btnConectar;
	private JButton btnEnviar;
	private JButton btnLimpar;
	private JButton btnSair;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane3;
	private JList<Object> listOnlines;
	private JTextArea txtAreaReceive;
	private JTextArea txtAreaSend;
	private JTextField txtName;
	private ClienteService service;
	
	//CONSTRUCTOR
	public Client(String nomeClient, ImageIcon image) {
		super("Chat: " + nomeClient);
		this.nomeClient = nomeClient;
		this.image = image;
		
        //LANCH GUI
		gui_initComponents();
        gui_lastInstructions();
        gui_start();  
    }
	
	public class In implements Runnable {

		//ObjectInputStream
		private ObjectInputStream input;

		//CONSTRUCTOR
		public In(Socket socket) {
			try {
				this.input = new ObjectInputStream(socket.getInputStream());
			} catch (IOException ex) {
				logger.getLog().info("IOException="+ex);
			}
		}

		//RUN
		public void run() {
			try {
				ChatMessage message = (ChatMessage) this.input.readObject();
				while ( message != null) {
					ChatMessage.Action action = message.getAction();

					if (action.equals(ChatMessage.Action.CONNECT)) {
						in_connect(message);
					} else if (action.equals(ChatMessage.Action.DISCONNECT)) {
						in_disconnected();
						getSocket().close();
					} else if (action.equals(ChatMessage.Action.SEND_ONE)) {
						in_receive(message);
					} else if (action.equals(ChatMessage.Action.USERS_ONLINE)) {
						in_refreshOnlines(message);
					}
				}	
			} catch (ClassNotFoundException ex) {
				logger.getLog().info("ClassNotFoundException="+ex);

			} catch (IOException ex) {
				logger.getLog().info("IOException="+ex);
			} 
		}

	}
	
	//GETTERS
    public String getNomeClient() {
		return nomeClient;
	}
	
	/** MAIN */
    public static void main(String[] args) {   	
    	//INITIALIZE
//    	new Client("Americo", new ImageIcon("D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurencyAndDistribution_MODULES\\src\\gui\\swing\\icon_and_text\\img\\dragao1.jpg"));
    	new Client("Tomas", new ImageIcon("D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurencyAndDistribution_MODULES\\src\\gui\\swing\\icon_and_text\\img\\dragao2.jpg"));
    }
	
    /** INIT: */
    private void gui_lastInstructions() {
    	//setDefaultLookAndFeelDecorated(true);
     	setSize(700, 500);
    	setLocation(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /** START: */
    private void gui_start() {
    	setVisible(true);
    }
    
	//ligacao com o servidor
	private void configurarRede() throws Exception {
	
		try {
			socket = new Socket("127.0.0.2", 6002);
			escritor = new PrintWriter(socket.getOutputStream());
			leitor = new Scanner(socket.getInputStream());
			new Thread(new Out()).start();
		} catch (Exception e) {}
	}
		
//	//class de escuta do servidor
//	private class EscutaServidor implements Runnable {
//
//		@Override
//		public void run() {
//			try {
//				String texto;
//				while((texto = leitor.nextLine()) != null) {
//					//ADD NEW TEXT AND IMAGE
//			        listModel.addElement(image);
//			        listModel.addElement(texto + "\n");
//			        listModel.addElement("[status = " + Message.SENT.toString() + "]"); 
//			        
////			        ImgNText myUser = new ImgNText("Americo", new ImageIcon("D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurencyAndDistribution_MODULES\\src\\gui\\swing\\icon_and_text\\img\\Americo.jpg"));
////			        ImageIcon image = new ImageIcon("D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurencyAndDistribution_MODULES\\src\\gui\\swing\\icon_and_text\\img\\Americo.jpg");
////			        listModel.addElement(image);
////			        listModel.addElement("Americo");
////			        listModel.addElement(myUser.getFotoUser());
//				}
//			} catch(Exception x) {}
//		}
//		
//	}
	
	/** */
	private void gui_initComponents() {
		
		//TABS
        setTitle("QuequeAPP");
        JTabbedPane jtp = new JTabbedPane();
        getContentPane().add(jtp);
        
        //PANEL1
        JPanel envio = new JPanel();
        JLabel label1 = new JLabel();
        label1.setText("MY CONTACTS");  
        envio.add(label1);
        
        //PANEL2
        JPanel jp2 = new JPanel();
        
        this.jPanel1 = new JPanel();
		this.txtName = new javax.swing.JTextField();
		this.btnConectar = new JButton();
		this.btnSair = new JButton();
		this.jPanel2 = new JPanel();
		this.jScrollPane3 = new JScrollPane();
		this.listOnlines = new javax.swing.JList();
		this.jPanel3 = new JPanel();
		this.jScrollPane1 = new JScrollPane();
		this.txtAreaReceive = new JTextArea();
		this.jScrollPane2 = new JScrollPane();
		this.txtAreaSend = new JTextArea();
		this.btnEnviar = new JButton();
		this.btnLimpar = new JButton();

		setDefaultCloseOperation(3);

		this.jPanel1.setBorder(javax.swing.BorderFactory
				.createTitledBorder("CONNECT"));

		this.btnConectar.setText("Connect");
		this.btnConectar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnConectarActionPerformed(evt);
			}

		});
		this.btnSair.setText("Exit");
		this.btnSair.setEnabled(false);
		this.btnSair.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnSairActionPerformed(evt);
			}

		});
		GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
		this.jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addComponent(this.txtName)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(this.btnConectar)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(this.btnSair)
										.addContainerGap()));

		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(this.txtName, -2, -1, -2)
						.addComponent(this.btnConectar)
						.addComponent(this.btnSair)));

		this.jPanel2.setBorder(javax.swing.BorderFactory
				.createTitledBorder("USERS ONLINE"));

		this.jScrollPane3.setViewportView(this.listOnlines);

		GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
		this.jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane3,
				-1, 157, 32767));

		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				jPanel2Layout.createSequentialGroup().addGap(0, 0, 32767)
						.addComponent(this.jScrollPane3, -2, 266, -2)));

		this.jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		this.txtAreaReceive.setEditable(false);
		this.txtAreaReceive.setColumns(20);
		this.txtAreaReceive.setRows(5);
		this.txtAreaReceive.setEnabled(false);
		this.jScrollPane1.setViewportView(this.txtAreaReceive);

		this.txtAreaSend.setColumns(20);
		this.txtAreaSend.setRows(5);
		this.txtAreaSend.setEnabled(false);
		this.jScrollPane2.setViewportView(this.txtAreaSend);

		this.btnEnviar.setText("Send");
		this.btnEnviar.setEnabled(false);
		this.btnEnviar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnEnviarActionPerformed(evt);
			}

		});
		this.btnLimpar.setText("Clear");
		this.btnLimpar.setEnabled(false);
		this.btnLimpar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnLimparActionPerformed(evt);
			}

		});
		GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
		this.jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout
				.setHorizontalGroup(jPanel3Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																this.jScrollPane1,
																-1, 355, 32767)
														.addComponent(
																this.jScrollPane2)
														.addGroup(
																GroupLayout.Alignment.TRAILING,
																jPanel3Layout
																		.createSequentialGroup()
																		.addGap(0,
																				0,
																				32767)
																		.addComponent(
																				this.btnLimpar)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				this.btnEnviar)))
										.addContainerGap()));

		jPanel3Layout
				.setVerticalGroup(jPanel3Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(this.jScrollPane1, -2,
												118, -2)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												-1, 32767)
										.addComponent(this.jScrollPane2, -2,
												56, -2)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.btnEnviar)
														.addComponent(
																this.btnLimpar))
										.addContainerGap()));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING,
												false)
												.addComponent(this.jPanel1, -1,
														-1, 32767)
												.addComponent(this.jPanel3, -1,
														-1, 32767))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(this.jPanel2, -1, -1, 32767)));

		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(this.jPanel1, -2, -1, -2)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(this.jPanel3, -1, -1, 32767)
								.addContainerGap())
				.addGroup(
						layout.createSequentialGroup().addContainerGap()
								.addComponent(this.jPanel2, -1, -1, 32767)));

		pack();
        
//		//INSTANTIATE
//		this.jp4 = new JPanel();
//		this.txtName = new JTextField();
//		this.btnConectar = new JButton();
//		this.btnSair = new JButton();
//		this.jp5 = new JPanel();
//		this.jScrollPane3 = new JScrollPane();
//		this.listOnlines = new JList();
//		this.jp6 = new JPanel();
//		this.jScrollPane1 = new JScrollPane();
//		this.txtAreaReceive = new JTextArea();
//		this.jScrollPane2 = new JScrollPane();
//		this.txtAreaSend = new JTextArea();
//		this.btnEnviar = new JButton();
//		this.btnLimpar = new JButton();
//		
//        
//        //TEXTFIELD COMPONENT
//		Font fonteTextfield = new Font("Serif", Font.LAYOUT_LEFT_TO_RIGHT, 20);
//		textoParaEnviar = new JTextField();
//		textoParaEnviar.setBackground(Color.BLUE);
//		textoParaEnviar.setForeground(Color.WHITE);
//		textoParaEnviar.setSize(200, 200);
//		textoParaEnviar.setFont(fonteTextfield);
//		
//        //BUTTON COMPONENT
//		JButton send = new JButton("Send");
//		Font fonteButton = new Font("Serif", Font.BOLD, 30);
//		send.setBackground(Color.WHITE);
//		send.setForeground(Color.GREEN);
//		send.setFont(fonteButton);
//		  try {
//		    Image img = ImageIO.read(getClass().getResource("/gui/listener/img/send.jpg"));
//		    send.setIcon(new ImageIcon(img));
//		  } catch (IOException ex) {
//		  }
//		
////		//TEXTAREA COMPONENT
////		textoRecebido = new JTextArea();
//        
//		//DefaultListModel
//        listModel = new DefaultListModel<>();
//        listModel.clear();
//        
//        //JLIST
//        countryList = new JList<>(listModel);
//        countryList.setFont(fonteTextfield);
//		scroll = new JScrollPane(countryList);
//		
//  		//DISTRIBUITON
//  		try {
//			configurarRede();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//  		
//  		//LISTENER
//		Send sendListener = new Send(send, textoParaEnviar, escritor, nomeClient);
//		send.addActionListener(sendListener);
//		textoParaEnviar.addKeyListener(sendListener);
//		
//		//PANEL3
//		JPanel jp3 = new JPanel();
//		JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL);
//		jp3.setLayout(new BoxLayout(jp3, BoxLayout.X_AXIS));
//
//	    BoundedRangeModel brm = textoParaEnviar.getHorizontalVisibility();
//	    scrollBar.setModel(brm);
//	    jp3.add(textoParaEnviar);
//	    jp3.add(send);
//	    jp3.add(scrollBar);
//	    
//	    //PANEL4
//		jp4.setLayout(new BoxLayout(jp4, BoxLayout.X_AXIS));
//		jp4.setBorder(BorderFactory.createTitledBorder("CONNECT"));
//
//		//BUTTON CONNECT
//		btnConectar.setText("Connect");
//		btnConectar.addActionListener(new Connect(btnConectar, this));
//		
//		//BUTTON EXIT
//		btnSair.setText("Exit");
//		btnSair.setEnabled(false);
//		btnSair.addActionListener(new Exit(btnSair, this));
//
//		jp4.add(txtName);
//		jp4.add(btnConectar);
//		jp4.add(btnSair);
//		
//		//PANEL5
//		jp5.setBorder(BorderFactory.createTitledBorder("USERS ONLINE"));
//		jScrollPane3.setViewportView(this.listOnlines);
//
//		jp5.add(jScrollPane3);
//		
//		//PANEL6
//		this.jp6.setBorder(BorderFactory.createEtchedBorder());
//
//		this.txtAreaReceive.setEditable(false);
//		this.txtAreaReceive.setColumns(20);
//		this.txtAreaReceive.setRows(5);
//		this.txtAreaReceive.setEnabled(false);
//		this.jScrollPane1.setViewportView(this.txtAreaReceive);
//
//		this.txtAreaSend.setColumns(20);
//		this.txtAreaSend.setRows(5);
//		this.txtAreaSend.setEnabled(false);
//		this.jScrollPane2.setViewportView(this.txtAreaSend);
		
//		jp6.add(jScrollPane2);
		

//		//BUTTON ENVIAR
//		btnEnviar.setText("Send");
//		btnEnviar.setEnabled(false);
//		btnEnviar.addActionListener(new Enviar(btnEnviar, this));
//
//		//BUTTON CLEAN
//		btnLimpar.setText("Clear");
//		btnLimpar.setEnabled(false);
//		btnLimpar.addActionListener(new Enviar(btnLimpar, this));
		
//		jp6.add(jScrollPane2);
//		jp6.add(btnEnviar);
//		jp6.add(btnLimpar);

		jp2.setLayout(new BorderLayout());
//		jp2.add(BorderLayout.SOUTH, jp3);
//		jp2.add(BorderLayout.NORTH, jp4);
//		jp2.add(BorderLayout.WEST, jp5);
//		jp2.add(BorderLayout.EAST, jScrollPane1);
//		jp2.add(BorderLayout.CENTER, scroll);
//		jp2.add(BorderLayout.SOUTH, jp6);

        
        //ADD PANELS TO TABS
		jtp.addTab("CHAT", jp2);
        jtp.addTab("CONTACTS", envio);
   
	}
	
	/** */
	public void in_connect(ChatMessage message) {
		
		if (message.getText().equals("NO")) {
			this.txtName.setText("");
			logger.getLog().info("CONNECTION FAILERD.\n TRY AGAIN WITH A DIFERENT NAME");
			return;
		}

		this.message = message;
		this.btnConectar.setEnabled(false);
		this.txtName.setEditable(false);
		this.btnSair.setEnabled(true);
		this.txtAreaSend.setEnabled(true);
		this.txtAreaReceive.setEnabled(true);
		this.btnEnviar.setEnabled(true);
		this.btnLimpar.setEnabled(true);

		logger.getLog().info("CONNECTION SUCCEDED.\n YOU ARE CONNECTED IN CHATROOM");
	}

	/** */
	public void in_disconnected() {
		this.btnConectar.setEnabled(true);
		this.txtName.setEnabled(true);

		this.btnSair.setEnabled(false);
		this.txtAreaSend.setEnabled(false);
		this.txtAreaReceive.setEnabled(false);
		this.btnEnviar.setEnabled(false);
		this.btnLimpar.setEnabled(false);

		this.txtAreaReceive.setText("");
		this.txtAreaSend.setText("");

		logger.getLog().info("YOU HAVE LEFT THE CHATROOM");
	}

	/** */
	public void in_receive(ChatMessage message) {
		this.txtAreaReceive.append(message.getName() + " SAID: "
				+ message.getText() + "\n");
	}

	/** */
	public void in_refreshOnlines(ChatMessage message) {
		System.out.println(message.getSetOnlines().toString());
		java.util.Set<String> names = message.getSetOnlines();

		names.remove(message.getName());

		String[] array = (String[]) names.toArray(new String[names.size()]);

		this.listOnlines.setListData(array);
		this.listOnlines.setSelectionMode(0);
		this.listOnlines.setLayoutOrientation(0);
	}

//	/** */
//	public Socket getSocket() {
//		return socket;
//	}
	
	/** */
	public void btnConectarActionPerformed() {
		String name = this.txtName.getText();

		if (!name.isEmpty()) {
			this.message = new ChatMessage();
			this.message.setAction(ChatMessage.Action.CONNECT);
			this.message.setName(name);

			this.service = new ClienteService();
			this.socket = this.service.connect();

			new Thread(new In(this.socket)).start();
			this.service.send(this.message);
		}
		this.txtAreaSend.setText("");
	}
	
	/** */
	public void btnSairActionPerformed() {
		this.message.setAction(ChatMessage.Action.DISCONNECT);
		this.service.send(this.message);
		disconnected();
	}
	
	/** */
	public void btnEnviarActionPerformed() {
		String text = this.txtAreaSend.getText();
		String name = this.message.getName();

		this.message = new ChatMessage();

		if (this.listOnlines.getSelectedIndex() > -1) {
			this.message.setNameReserved((String) this.listOnlines
					.getSelectedValue());
			this.message.setAction(ChatMessage.Action.SEND_ONE);
			this.listOnlines.clearSelection();
		} else {
			this.message.setAction(ChatMessage.Action.SEND_ALL);
		}

		if (!text.isEmpty()) {

			this.message.setName(name);
			this.message.setText(text);

			this.txtAreaReceive.append("YOU SAID: " + text + "\n");

			this.service.send(this.message);
		}
		this.txtAreaSend.setText("");
	}
	
	/** */
	public void btnCleanActionPerformed() {
		this.txtAreaSend.setText("");
	}
	

	/** */
	private void btnConectarActionPerformed(ActionEvent evt) {
		String name = this.txtName.getText();

		if (!name.isEmpty()) {
			this.message = new ChatMessage();
			this.message.setAction(ChatMessage.Action.CONNECT);
			this.message.setName(name);

			this.service = new ClienteService();
			this.socket = this.service.connect();

			new Thread(new ListenerSocket(this.socket)).start();
			this.service.send(this.message);
		}
		this.txtAreaSend.setText("");
	}

	private void btnSairActionPerformed(ActionEvent evt) {
		this.message.setAction(ChatMessage.Action.DISCONNECT);
		this.service.send(this.message);
		disconnected();
	}

	private void btnLimparActionPerformed(ActionEvent evt) {
		this.txtAreaSend.setText("");
	}

	private void btnEnviarActionPerformed(ActionEvent evt) {
		String text = this.txtAreaSend.getText();
		String name = this.message.getName();

		this.message = new ChatMessage();

		if (this.listOnlines.getSelectedIndex() > -1) {
			this.message.setNameReserved((String) this.listOnlines
					.getSelectedValue());
			this.message.setAction(ChatMessage.Action.SEND_ONE);
			this.listOnlines.clearSelection();
		} else {
			this.message.setAction(ChatMessage.Action.SEND_ALL);
		}

		if (!text.isEmpty()) {

			this.message.setName(name);
			this.message.setText(text);

			this.txtAreaReceive.append("YOU SAID: " + text + "\n");

			this.service.send(this.message);
		}
		this.txtAreaSend.setText("");
	}

	public java.net.Socket getSocket() {
		return socket;
	}
	
	private class ListenerSocket implements Runnable {
		private java.io.ObjectInputStream input;

		public ListenerSocket(java.net.Socket socket) {
			try {
				this.input = new java.io.ObjectInputStream(
						socket.getInputStream());
			} catch (java.io.IOException ex) {
				java.util.logging.Logger
						.getLogger(ClienteFrame.class.getName()).log(
								java.util.logging.Level.SEVERE, null, ex);
			}
		}

		public void run() {
			ChatMessage message = null;
			try {
				while ((message = (ChatMessage) this.input.readObject()) != null) {
					ChatMessage.Action action = message.getAction();

					if (action.equals(ChatMessage.Action.CONNECT)) {
						connect(message);
					} else if (action.equals(ChatMessage.Action.DISCONNECT)) {
						disconnected();
						socket.close();
					} else if (action.equals(ChatMessage.Action.SEND_ONE)) {
						receive(message);
					} else if (action.equals(ChatMessage.Action.USERS_ONLINE)) {
						refreshOnlines(message);
					}
				}
			} catch (java.io.IOException ex) {
				java.util.logging.Logger
						.getLogger(ClienteFrame.class.getName()).log(
								java.util.logging.Level.SEVERE, null, ex);
			} catch (ClassNotFoundException ex) {
				java.util.logging.Logger
						.getLogger(ClienteFrame.class.getName()).log(
								java.util.logging.Level.SEVERE, null, ex);
			}
		}
	}
	
	private void connect(ChatMessage message) {
		if (message.getText().equals("NO")) {
			this.txtName.setText("");
			javax.swing.JOptionPane.showMessageDialog(this,
					"CONNECTION FAILERD.\n TRY AGAIN WITH A DIFERENT NAME");
			return;
		}

		this.message = message;

		this.btnConectar.setEnabled(false);
		this.txtName.setEditable(false);

		this.btnSair.setEnabled(true);
		this.txtAreaSend.setEnabled(true);
		this.txtAreaReceive.setEnabled(true);

		this.btnEnviar.setEnabled(true);
		this.btnLimpar.setEnabled(true);

		javax.swing.JOptionPane.showMessageDialog(this,
				"CONNECTION SUCCEDED.\n YOU ARE CONNECTED IN CHATROOM");
	}

	private void disconnected() {
		this.btnConectar.setEnabled(true);
		this.txtName.setEnabled(true);

		this.btnSair.setEnabled(false);
		this.txtAreaSend.setEnabled(false);
		this.txtAreaReceive.setEnabled(false);
		this.btnEnviar.setEnabled(false);
		this.btnLimpar.setEnabled(false);

		this.txtAreaReceive.setText("");
		this.txtAreaSend.setText("");

		javax.swing.JOptionPane.showMessageDialog(this,
				"YOU HAVE LEFT THE CHATROOM");
	}

	private void receive(ChatMessage message) {
		this.txtAreaReceive.append(message.getName() + " SAID: "
				+ message.getText() + "\n");
	}

	private void refreshOnlines(ChatMessage message) {
		System.out.println(message.getSetOnlines().toString());
		java.util.Set<String> names = message.getSetOnlines();

		names.remove(message.getName());

		String[] array = (String[]) names.toArray(new String[names.size()]);

		this.listOnlines.setListData(array);
		this.listOnlines.setSelectionMode(0);
		this.listOnlines.setLayoutOrientation(0);
	}
	
}