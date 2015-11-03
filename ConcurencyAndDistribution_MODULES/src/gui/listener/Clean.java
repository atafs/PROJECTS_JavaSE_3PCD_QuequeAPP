package gui.listener;

import gui.backup.GUI_tabs_v1_3;
import gui.client.backup.ClienteFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Clean implements ActionListener {

	//ATTRIBUTE
	private JButton btnClean;
	private GUI_tabs_v1_3 client;

	//CONSTRUCTOR
	public Clean(JButton btnClean, GUI_tabs_v1_3 client) {
		this.btnClean = btnClean;
		this.client = client;
	}
	
	//GETTER
	public JButton getBtnClean() {
		return btnClean;
	}

	//ACTION LISTENER
	@Override
	public void actionPerformed(ActionEvent arg0) {
		client.btnCleanActionPerformed();

	}
}