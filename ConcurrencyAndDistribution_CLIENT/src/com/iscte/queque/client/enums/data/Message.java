package com.iscte.queque.client.enums.data;

import com.iscte.queque.client.log.LogMessage;

public enum Message {
	PENDING("Have not been sent yet"), SENT("Sent Message!!");
	
	// ATTRIBUTES
	private final String text;
	private static LogMessage logger = new LogMessage();

	// CONSTRUCTOR
	private Message(final String text) {
		this.text = text;
	}
	
	// TOSTRING
	@Override
	public String toString() {
		return text;
	}
	
	/** SELECT ACTION */
	public static void selecionaAccaoMensagem(Message estado) {
		try {
			switch (estado) {
			case PENDING:
				//...
				break;
					
			case SENT:
				//...
				break;

			default:
				logger.getLog().info("THE APP WENT TO DEFAUT VALUE IN MENSAGEM_ESTADO...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}