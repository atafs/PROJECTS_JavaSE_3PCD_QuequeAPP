package com.iscte.queque.client.listener.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.iscte.queque.client.gui.ClientFrame;
import com.iscte.queque.client.listener.QuequeListener;

public class BtnSair extends QuequeListener implements ActionListener{

	//CONSTRUCTOR
	public BtnSair(ClientFrame gui) {
		super(gui);
	}

	//ACTIONLISTENER
	@Override
	public void actionPerformed(ActionEvent evt) {
		super.getGui().btnSairActionPerformed(evt);
	}
}
