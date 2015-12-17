package com.iscte.queque._4serializable._v12_wait_offline.listener.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.iscte.queque._4serializable._v12_wait_offline.ClientMain;
import com.iscte.queque._4serializable._v12_wait_offline.listener.QuequeListener;

public class BtnOnline extends QuequeListener implements ActionListener{

	//CONSTRUCTOR
	public BtnOnline(ClientMain gui) {
		super(gui);
	}

	//ACTIONLISTENER
	@Override
	public void actionPerformed(ActionEvent evt) {
		super.getGui().btnOnlineActionPerformed(super.getGui(), evt);
	}

}
