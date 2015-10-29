package gui.listener;

import gui.backup.GUI_tabs_v1_3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Connect implements ActionListener {

	//ATTRIBUTE
	private JButton btnConectar;
	private GUI_tabs_v1_3 client;

	//CONSTRUCTOR
	public Connect(JButton btnConectar, GUI_tabs_v1_3 client) {
		this.btnConectar = btnConectar;
		this.client = client;
	}
	
	//GETTER
	public JButton getBtnConnect() {
		return btnConectar;
	}

	//ACTION LISTENER
	@Override
	public void actionPerformed(ActionEvent arg0) {
		client.btnConectarActionPerformed();
	}
	

}
