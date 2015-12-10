package com.iscte.queque.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
import com.iscte.queque.client.listener.chat.TxtFieldSend;
import com.iscte.queque.client.listener.contact.BtnAdd;
import com.iscte.queque.client.listener.contact.BtnClear;
import com.iscte.queque.client.listener.contact.BtnCreateNewGroup;
import com.iscte.queque.client.listener.contact.BtnNew;
import com.iscte.queque.client.listener.contact.BtnRemove;
import com.iscte.queque.client.log.LogMessage;
import com.iscte.queque.client.serializable.ChatMessage;
import com.iscte.queque.client.service.ClientService;
import com.iscte.queque.client.thread.In;

public class ClientFrame extends JFrame {

	//ATTRIBUTES
	private Socket socket;
	private ChatMessage message;
	private ClientService service;

	//TAB CHAT ITEMS ################################
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
	private JTextField txtFieldSend;
	private JTextField txtName;
	
	//TAB CONTACTS ITEMS ############################
	private JPanel jPanel4;
	private JButton btnNew;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnClear;
	private JPanel jPanel5;
	private JTextField txtNameGroup;
	private JButton  btnCreateNewGroup;
	
	//LOG4J LOGGER
	private static LogMessage logger = new LogMessage();
	private static final long serialVersionUID = 1L;
	
	//COUNTERS
	private int listOnlinesCounter;
	
	//TXT
	//PATHS (absolute for windows)
	private String userName = "";
	private final static String READ_FROM_FILE = "D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurrencyAndDistribution_CLIENT\\message\\txt\\readFromFile.txt";
	private final static String WRITE_TO_FILE = "D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurrencyAndDistribution_CLIENT\\message\\txt\\writeToFile.txt";
	private final static String WRITE_TO_FILE_USER = "D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurrencyAndDistribution_CLIENT\\message\\txt\\";
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
		this.txtFieldSend.setEnabled(true);
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
		this.txtFieldSend.setText("");
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

		// PANEL contactPanel ###########################
		JPanel contactPanel = new JPanel();
		JLabel label1 = new JLabel();
		label1.setText("MY CONTACTS");
		contactPanel.add(label1);
		
		//PANEL4: SOUTH
		this.jPanel4 = new JPanel();
		this.btnNew = new JButton();
		this.btnAdd = new JButton();
		this.btnRemove = new JButton();
		this.btnClear = new JButton();
		
		// PANEL4
		jPanel4.setBorder(BorderFactory.createTitledBorder("MANAGE CONTACTS (single/group)"));
		//FONT
		Font fonteButton1 = new Font("Serif", Font.BOLD, 15);
		
		btnNew.setText("New Group");
		btnNew.setBackground(Color.WHITE);
		btnNew.setForeground(Color.BLUE);
		btnNew.setFont(fonteButton1);
		  try {
		    Image img = ImageIO.read(getClass().getResource("/gui/listener/img/newGroup.png"));
		    btnNew.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
		  }
		btnNew.addActionListener(new BtnNew(this));

		btnAdd.setText("Add Contact");
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setForeground(Color.BLUE);
		btnAdd.setFont(fonteButton1);
		  try {
		    Image img = ImageIO.read(getClass().getResource("/gui/listener/img/newContact.jpg"));
		    btnAdd.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
		  }
		btnAdd.setEnabled(false);
		btnAdd.addActionListener(new BtnAdd(this));
		
		btnRemove.setText("Remove Contact");
		btnRemove.setBackground(Color.WHITE);
		btnRemove.setForeground(Color.BLUE);
		btnRemove.setFont(fonteButton1);
		  try {
		    Image img = ImageIO.read(getClass().getResource("/gui/listener/img/deleteContact.jpg"));
		    btnRemove.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
		  }
		btnRemove.setEnabled(false);
		btnRemove.addActionListener(new BtnRemove(this));
		
		btnClear.setText("Delete Group");
		btnClear.setBackground(Color.WHITE);
		btnClear.setForeground(Color.BLUE);
		btnClear.setFont(fonteButton1);
		  try {
		    Image img = ImageIO.read(getClass().getResource("/gui/listener/img/deleteGroup.png"));
		    btnClear.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
		  }
		btnClear.setEnabled(false);
		btnClear.addActionListener(new BtnClear(this));
		
		//BOX
		jPanel4.setLayout(new BoxLayout(jPanel4, BoxLayout.X_AXIS));
		
		//ADD
		jPanel4.add(btnNew);
		jPanel4.add(btnAdd);
		jPanel4.add(btnRemove);
		jPanel4.add(btnClear);
		
		// PANEL4 NORTH
		this.jPanel5 = new JPanel();
		this.jPanel5.setBorder(BorderFactory.createTitledBorder("GROUP NAME"));
		FlowLayout flow = new FlowLayout();
		flow.setAlignment(10);
		jPanel5.setLayout(flow);
		
		//TEXTFIELD
		Font fonteButton2 = new Font("Serif", Font.ITALIC, 15);
		this.txtNameGroup = new JTextField();
		this.txtNameGroup.setFont(fonteButton2);
		this.txtNameGroup.setEditable(false);
		this.txtNameGroup.setText("click the button New Group to insert name of new group first...");
		this.txtNameGroup.setSize(jPanel5.getWidth(), jPanel5.getHeight());

		//BUTTON
		this.btnCreateNewGroup = new JButton();
		this.btnCreateNewGroup.setFont(fonteButton2);
		btnCreateNewGroup.setText("Create");
		btnCreateNewGroup.setBackground(Color.WHITE);
		btnCreateNewGroup.setForeground(Color.BLUE);
		btnCreateNewGroup.setFont(fonteButton1);
		btnCreateNewGroup.setEnabled(false);
		btnCreateNewGroup.addActionListener(new BtnCreateNewGroup(this));

		//BOX
		jPanel5.setLayout(new BoxLayout(jPanel5, BoxLayout.X_AXIS));
		//ADD
		jPanel5.add(txtNameGroup);
		jPanel5.add(btnCreateNewGroup);
		
		//TAB contactPanel
		contactPanel.setLayout(new BorderLayout());
		contactPanel.add(BorderLayout.NORTH, jPanel5);
//		contactPanel.add(BorderLayout.EAST, jPanel6);
//		contactPanel.add(BorderLayout.CENTER, jPanel7);
//		contactPanel.add(BorderLayout.WEST, jPanel8);
		contactPanel.add(BorderLayout.SOUTH, jPanel4);

		// PANEL chatPanel ###########################
		JPanel chatPanel = new JPanel();

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
		this.txtFieldSend = new JTextField();
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
		//FONT
		Font fonteButton = new Font("Serif", Font.BOLD, 15);

		txtAreaReceive.setEditable(false);
		txtAreaReceive.setColumns(20);
		txtAreaReceive.setRows(5);
		txtAreaReceive.setEnabled(false);
		txtAreaReceive.setSize(chatPanel.getWidth(), chatPanel.getHeight());
		jScrollPane1.setViewportView(this.txtAreaReceive);

		txtFieldSend.setColumns(20);
//		txtAreaSend.setRows(2);
		txtFieldSend.setBackground(Color.BLUE);
		txtFieldSend.setForeground(Color.WHITE);
		txtFieldSend.setSize(200, 200);
		txtFieldSend.addKeyListener(new TxtFieldSend(this));
		txtFieldSend.setEnabled(false);
		jScrollPane2.setViewportView(this.txtFieldSend);

		btnEnviar.setText("Send");
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
		chatPanel.setLayout(new BorderLayout());
		chatPanel.add(BorderLayout.NORTH, jPanel1);
		chatPanel.add(BorderLayout.CENTER, jPanel2);
		chatPanel.add(BorderLayout.SOUTH, jPanel3);

		// ADD PANELS TO TABS #############################
		jtp.addTab("CHAT", chatPanel);
		jtp.addTab("CONTACTS", contactPanel);
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
		this.txtFieldSend.setText("");
	}

	public void btnSairActionPerformed(ActionEvent evt) {
//		this.message.setAction(ChatMessage.Action.DISCONNECT);
		this.service.send(this.message);
		disconnected();
	}
	
	public void btnAddActionPerformed(ActionEvent evt) {
		//...
	}
	
	public void btnClearActionPerformed(ActionEvent evt) {
		//...
	}
	
	public void btnNewActionPerformed(ActionEvent evt) {
		//TEXTFIELD AND BUTTON
		this.txtNameGroup.setEditable(true);
		this.btnCreateNewGroup.setEnabled(true);
		this.btnNew.setEnabled(false);
		this.txtNameGroup.setText("");
	}
	
	public void btnRemoveActionPerformed(ActionEvent evt) {
		//...
	}
	
	public void btnCreateNewGroupActionPerformed(ActionEvent evt) {
		//TEXTFIELD AND BUTTON
		this.txtNameGroup.setEditable(false);
		this.btnCreateNewGroup.setEnabled(false);
		this.btnNew.setEnabled(true);
		this.btnAdd.setEnabled(true);
		this.btnRemove.setEnabled(true);
		this.btnClear.setEnabled(true);
	}
	
	//AUX METHOD FOR clearTextField
	private void aux_clearTextField() {
		this.txtFieldSend.setText("");
	}
	
	//OVERLOAD FOR ActionEvent 
	public void btnLimparActionPerformed(ActionEvent evt) {
		aux_clearTextField();
	}
	
	//OVERLOAD FOR KeyEvent 
	public void btnLimparActionPerformed(KeyEvent evt) {
		aux_clearTextField();
	}

	//AUX METHOD FOR btnEnviarActionPerformed
	private void aux_enviarListener() {
		String text = this.txtFieldSend.getText();
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
		this.txtFieldSend.setText("");
	}
	
	//OVERLOAD FOR ActionEvent TO btnEnviarActionPerformed
	public void btnEnviarActionPerformed(ActionEvent evt) {
		aux_enviarListener();
	}
	
	//OVERLOAD FOR KeyEvent TO btnEnviarActionPerformed
	public void btnEnviarActionPerformed(KeyEvent e) {
		aux_enviarListener();
	}

	/** INIT: */
    private void gui_lastInstructions() {
    	//setDefaultLookAndFeelDecorated(true);
     	setSize(740, 500);
    	setLocation(200, 200);
    	setResizable(false);
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
