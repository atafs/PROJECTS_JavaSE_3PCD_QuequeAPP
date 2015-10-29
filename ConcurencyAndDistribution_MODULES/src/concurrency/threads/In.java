package concurrency.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import distribution.serializable.ChatMessage;
import log.LogMessage;
import gui.backup.GUI_tabs_v1_3;

public class In implements Runnable {

	//ObjectInputStream
	private ObjectInputStream input;
	//LOG4J LOGGER
	private static LogMessage logger = new LogMessage();
	private GUI_tabs_v1_3 clientFrame;

	//CONSTRUCTOR
	public In(Socket socket, GUI_tabs_v1_3 clientFrame) {
		try {
			this.clientFrame = clientFrame;
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
					clientFrame.in_connect(message);
				} else if (action.equals(ChatMessage.Action.DISCONNECT)) {
					clientFrame.in_disconnected();
					clientFrame.getSocket().close();
				} else if (action.equals(ChatMessage.Action.SEND_ONE)) {
					clientFrame.in_receive(message);
				} else if (action.equals(ChatMessage.Action.USERS_ONLINE)) {
					clientFrame.in_refreshOnlines(message);
				}
			}	
		} catch (ClassNotFoundException ex) {
			logger.getLog().info("ClassNotFoundException="+ex);

		} catch (IOException ex) {
			logger.getLog().info("IOException="+ex);
		} 
	}

}
