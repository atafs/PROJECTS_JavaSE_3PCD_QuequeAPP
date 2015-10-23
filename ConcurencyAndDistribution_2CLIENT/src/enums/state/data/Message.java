package enums.state.data;

import log.LogMessage;

public enum Message {
	// CONSTANTS
	NOT_SENT_YET("Have not been sent yet"), SENDING("Sending now the  Message!!"), SENT("Sent Message!!");
	
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
			case NOT_SENT_YET:
				//...
				break;
					
			case SENDING:
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