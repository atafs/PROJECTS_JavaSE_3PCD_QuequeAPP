package control_3CD;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Start_SERVER_CLIENTE_05_client_windowGUI {
	
	public static Socket socket;
	public static int port = 2406;
	public static String ip = "";
	
	public static void main(String[] args) {
		
		try {
			String local;
			
			try {
				local = InetAddress.getLocalHost().getHostAddress() + ":" + port;
				
			} catch (Exception e) {
				local = "Network Error";
			}
			
			//System.out.println(local);
			ip = (String) JOptionPane.showInputDialog(null, "IP: ", "Info", JOptionPane.INFORMATION_MESSAGE, null, null, local);
			port = Integer.parseInt(ip.substring(ip.indexOf(":") + 1));
	
			//System.out.println(ip);
			//System.out.println(ip.substring(ip.indexOf(":") + 1));
			//System.out.println(ip.indexOf(":"));
			ip = ip.substring(0, ip.indexOf(":"));
		
			
			socket = new Socket(ip, port);
			
			String username = System.getProperty("user.name");
			username = (String) JOptionPane.showInputDialog(null, "Username: ", "Info", JOptionPane.INFORMATION_MESSAGE, null, null, username);
			
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(username);
			
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			String response = (String) ois.readObject();
			
			JOptionPane.showMessageDialog(null,  response, "Message", JOptionPane.INFORMATION_MESSAGE);
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,  "Error: " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

}
