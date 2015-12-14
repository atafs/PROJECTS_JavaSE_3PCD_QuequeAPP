package com.iscte.queque.client.enums.client;

import com.iscte.queque.client.log.LogMessage;

public enum Client {
	ON("Ligado - OnLine"), OFF("Desligado - OffLine");
	
	// ATTRIBUTES
	private final String text;
	private static LogMessage logger = new LogMessage();

	// CONSTRUCTOR
	private Client(final String text) {
		this.text = text;
	}
	
	// TOSTRING
	@Override
	public String toString() {
		return text;
	}
	
	/** SELECT ACTION */
	public static void selecionaAccaoMensagem(Client estado) {
		try {
			switch (estado) {
			case ON:
				//...
				break;
					
			case OFF:
				//...
				break;

			default:
				logger.getLog().info("THE APP WENT TO DEFAUT VALUE IN CLIENTE_ESTADO...");
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}