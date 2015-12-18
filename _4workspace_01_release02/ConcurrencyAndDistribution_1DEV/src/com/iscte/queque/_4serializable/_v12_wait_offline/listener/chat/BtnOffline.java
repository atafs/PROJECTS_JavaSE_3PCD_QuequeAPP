package com.iscte.queque._4serializable._v12_wait_offline.listener.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.iscte.queque._4serializable._v12_wait_offline.ClientMain;
import com.iscte.queque._4serializable._v12_wait_offline.listener.QuequeListener;

public class BtnOffline extends QuequeListener implements ActionListener{

	//CONSTRUCTOR
	public BtnOffline(ClientMain gui) {
		super(gui);
	}

	//ACTIONLISTENER
	@Override
	public void actionPerformed(ActionEvent evt) {
		super.getGui().btnOfflineActionPerformed(super.getGui(), evt);
	}
}
