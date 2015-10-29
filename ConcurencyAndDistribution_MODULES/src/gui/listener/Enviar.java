package gui.listener;

import gui.backup.GUI_tabs_v1_3;
import gui.client.backup.ClienteFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Enviar implements ActionListener {

	//ATTRIBUTE
	private JButton enviar;
	private GUI_tabs_v1_3 client;

	//CONSTRUCTOR
	public Enviar(JButton enviar, GUI_tabs_v1_3 client) {
		this.enviar = enviar;
		this.client = client;
	}
	
	//GETTER
	public JButton getEnviar() {
		return enviar;
	}

	//ACTION LISTENER
	@Override
	public void actionPerformed(ActionEvent arg0) {
		client.btnEnviarActionPerformed();

	}
}
