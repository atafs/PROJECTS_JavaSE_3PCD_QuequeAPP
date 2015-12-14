package com.iscte.queque.client.listener.contact;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.iscte.queque.client.gui.ClientFrame;
import com.iscte.queque.client.listener.QuequeListener;

public class BtnCreateNewGroup extends QuequeListener implements ActionListener{

	//CONSTRUCTOR
	public BtnCreateNewGroup(ClientFrame gui) {
		super(gui);
	}

	//ACTIONLISTENER
	@Override
	public void actionPerformed(ActionEvent evt) {
		super.getGui().btnCreateNewGroupActionPerformed(evt);
	}

}
