package com.iscte.queque._4serializable._v12_wait_offline.listener;

import com.iscte.queque._4serializable._v12_wait_offline.main.ClientMain;

public class QuequeListener {
	
	//ATTRIBUTES
	private ClientMain gui;

	//CONSTRUCTOR
	public QuequeListener(ClientMain gui) {
		this.gui = gui;
	}

	//GETTERS AND SETTERS
	public ClientMain getGui() {
		return gui;
	}
}
