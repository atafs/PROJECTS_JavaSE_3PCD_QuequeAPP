package pt.progJava.americoLib.control107.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import pt.progJava.americoLib.control107.service.ClienteService;
import pt.progJava.americoLib.control108.bean.ChatMessage;

public class ClientFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private Socket socket;
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
	private JList<String> listOnlines;
	private JTextArea txtAreaReceive;
	private JTextArea txtAreaSend;
	private JTextField txtName;
	private ClienteService service;

	public ClientFrame() {
		initComponents();
        gui_lastInstructions();
        gui_start();  
	}

	private class ListenerSocket implements Runnable {
		private java.io.ObjectInputStream input;

		public ListenerSocket(java.net.Socket socket) {
			try {
				this.input = new java.io.ObjectInputStream(
						socket.getInputStream());
			} catch (java.io.IOException ex) {
				java.util.logging.Logger.getLogger(ClientFrame.class.getName())
						.log(java.util.logging.Level.SEVERE, null, ex);
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
				java.util.logging.Logger.getLogger(ClientFrame.class.getName())
						.log(java.util.logging.Level.SEVERE, null, ex);
			} catch (ClassNotFoundException ex) {
				java.util.logging.Logger.getLogger(ClientFrame.class.getName())
						.log(java.util.logging.Level.SEVERE, null, ex);
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

	private void initComponents() {

		// TABS
		setTitle("QuequeAPP");
		JTabbedPane jtp = new JTabbedPane();
		getContentPane().add(jtp);

		// PANEL1
		JPanel envio = new JPanel();
		JLabel label1 = new JLabel();
		label1.setText("MY CONTACTS");
		envio.add(label1);

		// PANEL2
		JPanel jp2 = new JPanel();

		this.jPanel1 = new JPanel();
		this.txtName = new JTextField();
		this.btnConectar = new JButton();
		this.btnSair = new JButton();
		this.jPanel2 = new JPanel();
		this.jScrollPane3 = new JScrollPane();
		this.listOnlines = new JList<String>();
		this.jPanel3 = new JPanel();
		this.jScrollPane1 = new JScrollPane();
		this.txtAreaReceive = new JTextArea();
		this.jScrollPane2 = new JScrollPane();
		this.txtAreaSend = new JTextArea();
		this.btnEnviar = new JButton();
		this.btnLimpar = new JButton();

		// setDefaultCloseOperation(3);

		// PANEL1
		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("CONNECT"));
		btnConectar.setText("Connect");
		btnConectar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ClientFrame.this.btnConectarActionPerformed(evt);
			}

		});

		btnSair.setText("Exit");
		btnSair.setEnabled(false);
		btnSair.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ClientFrame.this.btnSairActionPerformed(evt);
			}
		});

		// PANEL2,4
		jPanel2.setBorder(BorderFactory.createTitledBorder("MESSAGES AND USERS ONLINE"));
		jScrollPane3.setViewportView(this.listOnlines);

		// PANEL3
		jPanel3.setBorder(BorderFactory.createEtchedBorder());

		txtAreaReceive.setEditable(false);
		txtAreaReceive.setColumns(20);
		txtAreaReceive.setRows(5);
		txtAreaReceive.setEnabled(false);
		txtAreaReceive.setSize(jp2.getWidth(), jp2.getHeight());
		jScrollPane1.setViewportView(this.txtAreaReceive);

		txtAreaSend.setColumns(20);
		txtAreaSend.setRows(5);
		txtAreaSend.setBackground(Color.BLUE);
		txtAreaSend.setForeground(Color.WHITE);
		txtAreaSend.setSize(200, 200);
		txtAreaSend.setEnabled(false);
		jScrollPane2.setViewportView(this.txtAreaSend);

		btnEnviar.setText("Send");
		Font fonteButton = new Font("Serif", Font.BOLD, 30);
		btnEnviar.setBackground(Color.WHITE);
		btnEnviar.setForeground(Color.BLUE);
		btnEnviar.setFont(fonteButton);
		  try {
		    Image img = ImageIO.read(getClass().getResource("/gui/listener/img/send.jpg"));
		    btnEnviar.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
		  }
		btnEnviar.setEnabled(false);
		btnEnviar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ClientFrame.this.btnEnviarActionPerformed(evt);
			}

		});
		
		btnLimpar.setText("Clear");
		btnLimpar.setBackground(Color.WHITE);
		btnLimpar.setForeground(Color.BLUE);
		btnLimpar.setFont(fonteButton);
		  try {
		    Image img = ImageIO.read(getClass().getResource("/gui/listener/img/clean.png"));
		    btnLimpar.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
		  }
		btnLimpar.setEnabled(false);
		btnLimpar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ClientFrame.this.btnLimparActionPerformed(evt);
			}

		});

		//BOX
		jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.X_AXIS));
		jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.X_AXIS));
		jPanel3.setLayout(new BoxLayout(jPanel3, BoxLayout.X_AXIS));
		
		//ADD
		jPanel1.add(txtName);
		jPanel1.add(btnConectar);
		jPanel1.add(btnSair);
		
		jPanel3.add(jScrollPane2);
		jPanel3.add(btnEnviar);
		jPanel3.add(btnLimpar);
		
		jPanel2.add(jScrollPane1);
		jPanel2.add(jScrollPane3);

		//TAB1
		jp2.setLayout(new BorderLayout());
		jp2.add(BorderLayout.NORTH, jPanel1);
		jp2.add(BorderLayout.CENTER, jPanel2);
		jp2.add(BorderLayout.SOUTH, jPanel3);

		// ADD PANELS TO TABS
		jtp.addTab("CHAT", jp2);
		jtp.addTab("CONTACTS", envio);
	}

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

	/** INIT: */
    private void gui_lastInstructions() {
    	//setDefaultLookAndFeelDecorated(true);
     	setSize(900, 500);
    	setLocation(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /** START: */
    private void gui_start() {
    	setVisible(true);
    }
    
}
