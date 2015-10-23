package enums.state.server;

import log.LogMessage;

public enum Server {
	ON("Ligado - OnLine"), OFF("Desligado - OffLine");
	
	// ATTRIBUTES
	private final String text;
	private static LogMessage logger = new LogMessage();

	// CONSTRUCTOR
	private Server(final String text) {
		this.text = text;
	}
	
	// TOSTRING
	@Override
	public String toString() {
		return text;
	}
	
	/** SELECT ACTION */
	public static void selecionaAccaoMensagem(Server estado) {
		try {
			switch (estado) {
			case ON:
				//...
				break;
					
			case OFF:
				//...
				break;

			default:
				logger.getLog().info("THE APP WENT TO DEFAUT VALUE IN SERVIDOR_ESTADO...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}