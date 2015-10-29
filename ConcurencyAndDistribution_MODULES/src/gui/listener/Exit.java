package gui.listener;

import gui.backup.GUI_tabs_v1_3;
import gui.client.backup.ClienteFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Exit implements ActionListener {

	//ATTRIBUTE
	private JButton exit;
	private GUI_tabs_v1_3 client;

	//CONSTRUCTOR
	public Exit(JButton exit, GUI_tabs_v1_3 client) {
		this.exit = exit;
		this.client = client;
	}
	
	//GETTER
	public JButton getExit() {
		return exit;
	}

	//ACTION LISTENER
	@Override
	public void actionPerformed(ActionEvent arg0) {
		client.btnSairActionPerformed();

	}
}
