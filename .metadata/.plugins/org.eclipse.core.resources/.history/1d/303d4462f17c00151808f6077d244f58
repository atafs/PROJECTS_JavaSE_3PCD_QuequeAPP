package control_3CD;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pt.progJava.americoLib.control27.control.DataPackage;

public class Start_SERVER_CLIENTE_05_server_windowGUI {

	/* attributes */
	public static int port = 2406;
	public static String ip = "";

	public static ServerSocket server;
	
	public static ArrayList<Socket> list_sockets = new ArrayList<Socket>();
	public static ArrayList<Integer> list_cliente_states = new ArrayList<Integer>();
	public static ArrayList<DataPackage> list_data = new ArrayList<DataPackage>();
	
	private static Runnable accept = new Runnable() {
		
		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			new Thread(send).start();
			new Thread(receive).start();
			
			while(true) {
				try {
					Socket socket = server.accept();
					
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					
					String username = (String) ois.readObject();
					
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject("Welcome to americoSERVER!!!");
					
					list_clients_model.addElement(username + " - " + socket.getInetAddress().getHostAddress() + " - " + socket.getInetAddress().getHostName());
					list_cliente_states.add(0);
					
					list_data.add(new DataPackage());
					list_sockets.add(socket);
					
					
				} catch (Exception e) {
					
				}
			}
		}
	};
	
	private static Runnable receive = new Runnable() {
		
		@Override
		public void run() {

			ObjectInputStream ois;
			
			while(true) {
				for (int i = 0; i < list_sockets.size(); i++) {
					
					try {
						ois = new ObjectInputStream(list_sockets.get(i).getInputStream());
						int receive_state = (Integer) ois.readObject();
						
						ois = new ObjectInputStream(list_sockets.get(i).getInputStream());
						DataPackage dp = (DataPackage) ois.readObject();
						
						list_data.set(i, dp);
						
						if(receive_state == 1) {//cliente disconnected by user
							disconectClient(i);
							i--;
						} 
					} catch (Exception e) {//client disconnected (client did not notify server about disconnecting)
						disconectClient(i);
						i--;
					}
				}
			}
		}
	};
	
	private static Runnable send = new Runnable() {
		
		@Override
		public void run() {

			ObjectOutputStream oos;
			while(true) {
				
				for (int i = 0; i < list_sockets.size(); i++) {
					try {
						oos = new ObjectOutputStream(list_sockets.get(i).getOutputStream());
						int client_state = list_cliente_states.get(i);
						oos.writeObject(client_state);
						
						oos = new ObjectOutputStream(list_sockets.get(i).getOutputStream());
						oos.writeObject(list_data);
						
						if(client_state == 1) {//kicked by server
							disconectClient(i);
							i--;
							System.out.println("kicked by server");
						} else if(client_state == 2) {//disconnected by server
							disconectClient(i);
							i--;
							System.out.println("disconnected by server");
						}
					} catch (Exception e) {
					
					}
				}
			}
		}
	};
	
	public static void disconectClient(int index) {
	
		try {
			list_clients_model.removeElementAt(index);
			list_cliente_states.remove(index);
			list_data.remove(index);
			list_sockets.remove(index);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static JFrame frame;
	public static JPanel content;
	public static JPanel panel1;
	public static JPanel panel2;
	public static JPanel panel3;

	public static JButton btn_disconnect;
	@SuppressWarnings("rawtypes")
	public static JList list_client;
	@SuppressWarnings("rawtypes")
	public static DefaultListModel list_clients_model;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		try {
			
			ip = InetAddress.getLocalHost().getHostAddress() + ":" + port;
			server = new ServerSocket(port, 0, InetAddress.getLocalHost());
			new Thread(accept).start();;

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(),
					", ALERT", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		btn_disconnect = new JButton();
		btn_disconnect.setText("Disconnect");
		btn_disconnect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selected = list_client.getSelectedIndex();

				if (selected != -1) {

					try {
						list_cliente_states.set(selected, 1);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "ERROR: " + e2.getMessage(), "ALERT", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		list_clients_model = new DefaultListModel<>();
		list_client = new JList<>(list_clients_model);
		list_client.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (e.getValueIsAdjusting()) {
					System.out.println(list_client.getSelectedIndex());
				}
			}
		});

		frame = new JFrame();
		frame.setTitle("SERVER - " + ip);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				while(list_sockets.size() != 0) {
					
					try {
						for (int i = 0; i < list_cliente_states.size(); i++) {
							list_cliente_states.set(i, 2);
						}
					} catch (Exception e) {
						
					}
				}
				
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1, 1, 1, 1));
		panel1.add(btn_disconnect);
		
		panel2 = new JPanel();
		panel2.add(new JLabel(ip));
		
		panel3 = new JPanel();
		panel3.setLayout(new BorderLayout(1,1));
		panel3.add(panel1, BorderLayout.NORTH);
		panel3.add(new JScrollPane(list_client), BorderLayout.CENTER);
		panel3.add(panel2, BorderLayout.SOUTH);
		
		content = new JPanel();
		content.setLayout(new GridLayout(1,1,1,1));
		content.add(panel3);
		
		content.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		frame.setContentPane(content);
		frame.pack();
		frame.setSize(350,400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

}
