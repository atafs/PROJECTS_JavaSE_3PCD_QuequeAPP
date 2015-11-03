package serializable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

public class ChatMessage implements Serializable {

	//ATTRIBUTES
	private static final long serialVersionUID = 1L;
	private String name;
	private ImageIcon image;
	private String text;
	private String nameReserved;
	private Set<String> setOnlines;
	private Action action;

	//CONSTRUCTOR
	public ChatMessage() {
		this.setOnlines = new HashSet<String>();
	}

	//GETTERS AND SETTERS
	public String getName() {
		return this.name;
	}

	public String getText() {
		return this.text;
	}

	public String getNameReserved() {
		return this.nameReserved;
	}

	public Set<String> getSetOnlines() {
		return this.setOnlines;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setNameReserved(String nameReserved) {
		this.nameReserved = nameReserved;
	}

	public void setSetOnlines(Set<String> setOnlines) {
		this.setOnlines = setOnlines;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public Action getAction() {
		return this.action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	//ENUM (constants)
	public static enum Action {
		CONNECT, DISCONNECT, SEND_ONE, SEND_ALL, USERS_ONLINE;

		private Action() {
		}
	}
}