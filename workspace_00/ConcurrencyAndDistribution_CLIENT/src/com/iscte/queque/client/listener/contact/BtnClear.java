package com.iscte.queque.client.listener.contact;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.iscte.queque.client.gui.ClientFrame;
import com.iscte.queque.client.listener.QuequeListener;

public class BtnClear extends QuequeListener implements ActionListener{

	//CONSTRUCTOR
	public BtnClear(ClientFrame gui) {
		super(gui);
	}

	//ACTIONLISTENER
	@Override
	public void actionPerformed(ActionEvent evt) {
		super.getGui().btnClearActionPerformed(evt);
	}

}
