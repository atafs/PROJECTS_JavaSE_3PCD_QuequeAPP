package com.iscte.queque.client.listener;

import com.iscte.queque.client.gui.ClientFrame;

public class QuequeListener {
	
	//ATTRIBUTES
	private ClientFrame gui;

	//CONSTRUCTOR
	public QuequeListener(ClientFrame gui) {
		this.gui = gui;
	}

	//GETTERS AND SETTERS
	public ClientFrame getGui() {
		return gui;
	}
}
