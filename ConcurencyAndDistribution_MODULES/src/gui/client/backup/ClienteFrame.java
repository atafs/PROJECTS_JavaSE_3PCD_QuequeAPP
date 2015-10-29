package gui.client.backup;

import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import pt.progJava.americoLib.control107.service.ClienteService;
import pt.progJava.americoLib.control108.bean.ChatMessage;

public class ClienteFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private java.net.Socket socket;
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
	private javax.swing.JList listOnlines;
	private JTextArea txtAreaReceive;
	private JTextArea txtAreaSend;
	private javax.swing.JTextField txtName;
	private ClienteService service;

	public ClienteFrame() {
		initComponents();
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
						ClienteFrame.this.connect(message);
					} else if (action.equals(ChatMessage.Action.DISCONNECT)) {
						ClienteFrame.this.disconnected();
						ClienteFrame.this.socket.close();
					} else if (action.equals(ChatMessage.Action.SEND_ONE)) {
						ClienteFrame.this.receive(message);
					} else if (action.equals(ChatMessage.Action.USERS_ONLINE)) {
						ClienteFrame.this.refreshOnlines(message);
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

	private void initComponents() {
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
				ClienteFrame.this.btnConectarActionPerformed(evt);
			}

		});
		this.btnSair.setText("Exit");
		this.btnSair.setEnabled(false);
		this.btnSair.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ClienteFrame.this.btnSairActionPerformed(evt);
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
				ClienteFrame.this.btnEnviarActionPerformed(evt);
			}

		});
		this.btnLimpar.setText("Clear");
		this.btnLimpar.setEnabled(false);
		this.btnLimpar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ClienteFrame.this.btnLimparActionPerformed(evt);
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

	public java.net.Socket getSocket() {
		return socket;
	}
}
