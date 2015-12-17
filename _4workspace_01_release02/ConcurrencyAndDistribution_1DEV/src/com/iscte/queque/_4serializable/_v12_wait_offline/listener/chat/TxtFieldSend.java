package com.iscte.queque._4serializable._v12_wait_offline.listener.chat;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.iscte.queque._4serializable._v12_wait_offline.ClientMain;
import com.iscte.queque._4serializable._v12_wait_offline.listener.QuequeListener;

public class TxtFieldSend extends QuequeListener implements KeyListener{

	//CONSTRUCTOR
	public TxtFieldSend(ClientMain gui) {
		super(gui);
	}

	//KEYLISTENER
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		//ENTER KEY PRESSED
		if (key == KeyEvent.VK_ENTER) {
			Toolkit.getDefaultToolkit().beep();
			super.getGui().btnEnviarActionPerformed(e);
		}
		
		//DELETE KEY PRESSED
		if (key == KeyEvent.VK_DELETE) {
			Toolkit.getDefaultToolkit().beep();
			super.getGui().btnLimparActionPerformed(e);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {}

}
