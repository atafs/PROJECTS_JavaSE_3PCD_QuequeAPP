package com.iscte.queque.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.iscte.queque.client.io.read.Reader;
import com.iscte.queque.client.io.write.Writer;
import com.iscte.queque.client.listener.chat.BtnConectar;
import com.iscte.queque.client.listener.chat.BtnEnviar;
import com.iscte.queque.client.listener.chat.BtnLimpar;
import com.iscte.queque.client.listener.chat.BtnSair;
import com.iscte.queque.client.log.LogMessage;
import com.iscte.queque.client.serializable.ChatMessage;
import com.iscte.queque.client.service.ClientService;
import com.iscte.queque.client.thread.In;

public class ClientFrame extends JFrame {

	//ATTRIBUTES
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
	private ClientService service;
	//LOG4J LOGGER
	private static LogMessage logger = new LogMessage();
	private static final long serialVersionUID = 1L;
	
	//COUNTERS
	private int listOnlinesCounter;
	
	//TXT
	//PATHS (absolute for windows)
	private String userName = "";
	private final static String READ_FROM_FILE = "D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurencyAndDistribution_MODULES_v2\\message\\txt\\readFromFile.txt";
	private final static String WRITE_TO_FILE = "D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurencyAndDistribution_MODULES_v2\\message\\txt\\writeToFile.txt";
	private final static String WRITE_TO_FILE_USER = "D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurencyAndDistribution_MODULES_v2\\message\\txt\\";
	private static String userPATH = "";
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	private Reader reader = new Reader();
	private Writer writer = new Writer();
		

	//CONSTRUCTOR
	public ClientFrame(/*String userPATH*/) {
//		//this.userName = userName;
//		this.userPATH = userPATH;
//		
//		//READER
//		Reader reader = new Reader();
//		try {
//			List<String> text = new ArrayList<String>();
//			text = reader.readSmallTextFile(userPATH);
//			
//			
//			for (int i = 0; i < text.size(); i++) {
//				userName = text.get(i);
//				userName.split(" ");
//			}
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		gui_initComponents(/*userName*/);
        gui_lastInstructions();
        gui_start();  
	}
	
	//GETTERS AND SETTERS
	public Socket getSocket() {
		return socket;
	}

	public void connect(ChatMessage message) {
//		if (message.getText().equals("NO")) {
//			this.txtName.setText("");
//			JOptionPane.showMessageDialog(this, "CONNECTION FAILED.\n TRY AGAIN WITH A DIFERENT NAME");
//			return;
//		}

 		this.message = message;

		this.btnConectar.setEnabled(false);
		this.txtName.setEditable(false);

		this.btnSair.setEnabled(true);
		this.txtAreaSend.setEnabled(true);
		this.txtAreaReceive.setEnabled(true);

		this.btnEnviar.setEnabled(true);
		this.btnLimpar.setEnabled(true);

		JOptionPane.showMessageDialog(this,"CONNECTION SUCCEDED.\n YOU ARE CONNECTED IN CHATROOM");
	}

	public void disconnected() {
//		this.btnConectar.setEnabled(true);
//		this.txtName.setEnabled(true);
//
//		
//		this.txtAreaSend.setEnabled(true);
//		this.txtAreaReceive.setEnabled(true);
//		this.btnEnviar.setEnabled(true);
//		this.btnLimpar.setEnabled(true);

		this.txtAreaReceive.setText("");
		this.txtAreaSend.setText("");
		this.message.setState(ChatMessage.Action.OFFLINE);
		this.btnSair.setEnabled(false);
		JOptionPane.showMessageDialog(this,"YOU HAVE LEFT THE CHATROOM");
	}

	public void receive(ChatMessage message) {
		
		//TEXTAREA
		this.txtAreaReceive.append(message.getName() + " SAID: "+ message.getText() + "\n");
		
		//TXT
		logger.getLog().debug(message.getText());
		try {
			String path = WRITE_TO_FILE_USER + message.getName() + ".txt";
			writer.writeLargerTextFile(path, message.getName() + " SAID: "+ message.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void refreshOnlines(ChatMessage message) {
		System.out.println(message.getSetOnlines().toString());
		Set<String> names = message.getSetOnlines();

		names.remove(message.getName());

		String[] array = (String[]) names.toArray(new String[names.size()]);

		this.listOnlines.setListData(array);
		this.listOnlines.setSelectionMode(0);
		this.listOnlines.setLayoutOrientation(0);
	}

	private void gui_initComponents(/*String user*/) {

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

		//TEXTFIELD
//		txtName.setText(user);
		
		// setDefaultCloseOperation(3);

		// PANEL1
		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("CONNECT"));
		btnConectar.setText("ONLINE");
		btnConectar.addActionListener(new BtnConectar(this));

		btnSair.setText("OFFLINE");
		btnSair.setEnabled(false);
		btnSair.addActionListener(new BtnSair(this));

		// PANEL2,4
		jPanel2.setBorder(BorderFactory.createTitledBorder("MESSAGES AND USERS"));
//		listOnlines.addListSelectionListener(new ListSelectionListener() {
//			@Override
//			public void valueChanged(ListSelectionEvent e) {
//				System.err.println("selectedElement="+listOnlines.getSelectedIndex());
//			}
//		});
		
		listOnlines.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
//		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		            // Double-click detected
		            int index = listOnlines.locationToIndex(evt.getPoint());
					logger.getLog().debug("JLIST selectedElement=" + index + "; ");
					
					//READ FROM FILE
					String path = WRITE_TO_FILE_USER + message.getName() + ".txt";
					try {
						readLargerTextFileAlternate(path);
					} catch (IOException e) {
						e.printStackTrace();
					}

					
		        } else if (evt.getClickCount() == 3) {
		            // Triple-click detected
		            int index = listOnlines.locationToIndex(evt.getPoint());
					logger.getLog().debug("JLIST selectedElement=" + index + "; ");

		        }
		    }
		});
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
		btnEnviar.addActionListener(new BtnEnviar(this));
		
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
		btnLimpar.addActionListener(new BtnLimpar(this));

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

	public void btnConectarActionPerformed(ActionEvent evt) {
		String name = this.txtName.getText();

		if (!name.isEmpty()) {
			this.message = new ChatMessage();
			this.message.setAction(ChatMessage.Action.CONNECT);
			this.message.setState(ChatMessage.Action.ONLINE);
			this.message.setName(name);

			this.service = new ClientService();
			this.socket = this.service.connect();

			new Thread(new In(this, this.socket)).start();
			this.service.send(this.message);
		}
		this.txtAreaSend.setText("");
	}

	public void btnSairActionPerformed(ActionEvent evt) {
//		this.message.setAction(ChatMessage.Action.DISCONNECT);
		this.service.send(this.message);
		disconnected();
	}

	public void btnLimparActionPerformed(ActionEvent evt) {
		this.txtAreaSend.setText("");
	}

	public void btnEnviarActionPerformed(ActionEvent evt) {
		String text = this.txtAreaSend.getText();
		String name = this.message.getName();

		this.message = new ChatMessage();

		if (this.listOnlines.getSelectedIndex() > -1) {
			String selectedContact = (String) this.listOnlines.getSelectedValue();
			this.message.setNameReserved(selectedContact);
			this.message.setAction(ChatMessage.Action.SEND_ONE);
			//this.listOnlines.clearSelection();
		} else {
			JOptionPane.showMessageDialog(this,"PLEASE SELECT SOMEONE TO SEND A MESSAGE");
			return;
			//this.message.setAction(ChatMessage.Action.SEND_ALL);
		}

		if (!text.isEmpty()) {

			//NAME AND MESSAGE
			this.message.setName(name);
			this.message.setText(text);

			//PRINT TO TEXT_AREA AND TXT
			this.txtAreaReceive.append("YOU SAID: " + text + "\n");
			try {	
				writer.writeLargerTextFile(WRITE_TO_FILE, message.getName() + ": " + text);
			} catch (IOException e) {
				e.printStackTrace();
			}

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
    
	/** IO: For larger files */
	public void readLargerTextFile(String aFileName) throws IOException {
		Path path = Paths.get(aFileName);
		try (Scanner scanner = new Scanner(path, ENCODING.name())) {
			String tempText = "";
			while (scanner.hasNextLine()) {
				// process each line in some way
				//TEXTAREA
				tempText += scanner.nextLine() + "\n";
				logger.getLog().debug(scanner.nextLine());

			}
			this.txtAreaReceive.setText("");
			this.txtAreaReceive.append(tempText + "\n");

		}
	}
	
	/** IO: */
	public void readLargerTextFileAlternate(String aFileName) throws IOException {
		Path path = Paths.get(aFileName);
		try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				// process each line in some way
				//TEXTAREA
				line += line + "\n";
				logger.getLog().debug(line);
			}
			this.txtAreaReceive.setText("");
			this.txtAreaReceive.append(line + "\n");
		}
	}
    
}
