package tools;

import javafx.scene.paint.Color;

public class Message implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	private String message;
	public String message() { return message; }
	transient private Color colour;
	public Color colour() {
		if (colour != null)
			return colour;
		else
			return Color.WHITE;
	}
	
	public Message(String message, Color colour) {
		this.message = message;
		this.colour = colour;
	}
	public Message(String message) {
		this.message = message;
	}

}
