package gui.swing.icon_and_text;

import javax.swing.Icon;

public class ImgNText {
	
	//ATTRIBUTES
	private String nameUser;
	private Icon fotoUser;
	
	//CONSTRUCTOR
	public ImgNText(String nameUser, Icon fotoUser) {
		super();
		this.nameUser = nameUser;
		this.fotoUser = fotoUser;
	}
	
	//GETTERS AND SETTERS
	public String getNameUser() {
		return nameUser;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
	public Icon getFotoUser() {
		return fotoUser;
	}
	public void setFotoUser(Icon fotoUser) {
		this.fotoUser = fotoUser;
	}
	
	

}
