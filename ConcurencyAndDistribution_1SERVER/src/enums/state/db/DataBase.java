package enums.state.db;

import log.LogMessage;

public enum DataBase {
	CREATE("Insert Statement"), RETRIEVE("Select Statement"), UPDATE("Update Statement"), DELETE("Delete Statement");
	
	// ATTRIBUTES
	private final String text;
	private static LogMessage logger = new LogMessage();

	// CONSTRUCTOR
	private DataBase(final String text) {
		this.text = text;
	}
	
	// TOSTRING
	@Override
	public String toString() {
		return text;
	}
	
	/** SELECT ACTION */
	public static void selecionaAccaoMensagem(DataBase estado) {
		try {
			switch (estado) {
			case CREATE:
				//...
				break;
					
			case RETRIEVE:
				//...
				break;
			
			case UPDATE:
				//...
				break;
			
			case DELETE:
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
