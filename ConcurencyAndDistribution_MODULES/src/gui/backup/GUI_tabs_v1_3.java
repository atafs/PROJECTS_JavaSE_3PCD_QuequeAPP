package gui.backup;

import distribution.serializable.ChatMessage;
import distribution.service.ClienteService;
import gui.listener.Connect;
import gui.listener.Enviar;
import gui.listener.Exit;
import gui.listener.Send;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
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
 
public class GUI_tabs_v1_3 extends JFrame {
     
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
	private JPanel jp4;
	private JPanel jp5;
	private JPanel jp6;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane3;
	private JList<Object> listOnlines;
	private JTextArea txtAreaReceive;
	private JTextArea txtAreaSend;
	private JTextField txtName;
	private ClienteService service;
	
	//CONSTRUCTOR
	public GUI_tabs_v1_3(String nomeClient, ImageIcon image) {
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
    	new GUI_tabs_v1_3("Tomas", new ImageIcon("D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurencyAndDistribution_MODULES\\src\\gui\\swing\\icon_and_text\\img\\dragao2.jpg"));
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
		//INSTANTIATE
		this.jp4 = new JPanel();
		this.txtName = new JTextField();
		this.btnConectar = new JButton();
		this.btnSair = new JButton();
		this.jp5 = new JPanel();
		this.jScrollPane3 = new JScrollPane();
		this.listOnlines = new JList();
		this.jp6 = new JPanel();
		this.jScrollPane1 = new JScrollPane();
		this.txtAreaReceive = new JTextArea();
		this.jScrollPane2 = new JScrollPane();
		this.txtAreaSend = new JTextArea();
		this.btnEnviar = new JButton();
		this.btnLimpar = new JButton();
		
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
        
        //TEXTFIELD COMPONENT
		Font fonteTextfield = new Font("Serif", Font.LAYOUT_LEFT_TO_RIGHT, 20);
		textoParaEnviar = new JTextField();
		textoParaEnviar.setBackground(Color.BLUE);
		textoParaEnviar.setForeground(Color.WHITE);
		textoParaEnviar.setSize(200, 200);
		textoParaEnviar.setFont(fonteTextfield);
		
        //BUTTON COMPONENT
		JButton send = new JButton("Send");
		Font fonteButton = new Font("Serif", Font.BOLD, 30);
		send.setBackground(Color.WHITE);
		send.setForeground(Color.GREEN);
		send.setFont(fonteButton);
		  try {
		    Image img = ImageIO.read(getClass().getResource("/gui/listener/img/send.jpg"));
		    send.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
		  }
		
//		//TEXTAREA COMPONENT
//		textoRecebido = new JTextArea();
        
		//DefaultListModel
        listModel = new DefaultListModel<>();
        listModel.clear();
        
        //JLIST
        countryList = new JList<>(listModel);
        countryList.setFont(fonteTextfield);
		scroll = new JScrollPane(countryList);
		
  		//DISTRIBUITON
  		try {
			configurarRede();
		} catch (Exception e) {
			e.printStackTrace();
		}
  		
  		//LISTENER
		Send sendListener = new Send(send, textoParaEnviar, escritor, nomeClient);
		send.addActionListener(sendListener);
		textoParaEnviar.addKeyListener(sendListener);
		
		//PANEL3
		JPanel jp3 = new JPanel();
		JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL);
		jp3.setLayout(new BoxLayout(jp3, BoxLayout.X_AXIS));

	    BoundedRangeModel brm = textoParaEnviar.getHorizontalVisibility();
	    scrollBar.setModel(brm);
	    jp3.add(textoParaEnviar);
	    jp3.add(send);
	    jp3.add(scrollBar);
	    
	    //PANEL4
		jp4.setLayout(new BoxLayout(jp4, BoxLayout.X_AXIS));
		jp4.setBorder(BorderFactory.createTitledBorder("CONNECT"));

		//BUTTON CONNECT
		btnConectar.setText("Connect");
		btnConectar.addActionListener(new Connect(btnConectar, this));
		
		//BUTTON EXIT
		btnSair.setText("Exit");
		btnSair.setEnabled(false);
		btnSair.addActionListener(new Exit(btnSair, this));

		jp4.add(txtName);
		jp4.add(btnConectar);
		jp4.add(btnSair);
		
		//PANEL5
		jp5.setBorder(BorderFactory.createTitledBorder("USERS ONLINE"));
		jScrollPane3.setViewportView(this.listOnlines);

		jp5.add(jScrollPane3);
		
		//PANEL6
		this.jp6.setBorder(BorderFactory.createEtchedBorder());

		this.txtAreaReceive.setEditable(false);
		this.txtAreaReceive.setColumns(20);
		this.txtAreaReceive.setRows(5);
		this.txtAreaReceive.setEnabled(false);
		this.jScrollPane1.setViewportView(this.txtAreaReceive);

		this.txtAreaSend.setColumns(20);
		this.txtAreaSend.setRows(5);
		this.txtAreaSend.setEnabled(false);
		this.jScrollPane2.setViewportView(this.txtAreaSend);
		
//		jp6.add(jScrollPane2);
		

		//BUTTON ENVIAR
		btnEnviar.setText("Send");
		btnEnviar.setEnabled(false);
		btnEnviar.addActionListener(new Enviar(btnEnviar, this));

		//BUTTON CLEAN
		btnLimpar.setText("Clear");
		btnLimpar.setEnabled(false);
		btnLimpar.addActionListener(new Enviar(btnLimpar, this));
		
		jp6.add(jScrollPane2);
		jp6.add(btnEnviar);
		jp6.add(btnLimpar);

		jp2.setLayout(new BorderLayout());
//		jp2.add(BorderLayout.SOUTH, jp3);
		jp2.add(BorderLayout.NORTH, jp4);
		jp2.add(BorderLayout.WEST, jp5);
		jp2.add(BorderLayout.EAST, jScrollPane1);
//		jp2.add(BorderLayout.CENTER, scroll);
		jp2.add(BorderLayout.SOUTH, jp6);

        
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

	/** */
	public Socket getSocket() {
		return socket;
	}
	
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
	public void disconnected() {
		this.btnConectar.setEnabled(true);
		this.txtName.setEnabled(true);

		this.btnSair.setEnabled(false);
		this.txtAreaSend.setEnabled(false);
		this.txtAreaReceive.setEnabled(false);
		this.btnEnviar.setEnabled(false);
		this.btnLimpar.setEnabled(false);

		this.txtAreaReceive.setText("");
		this.txtAreaSend.setText("");

		JOptionPane.showMessageDialog(this, "YOU HAVE LEFT THE CHATROOM");
	}

}