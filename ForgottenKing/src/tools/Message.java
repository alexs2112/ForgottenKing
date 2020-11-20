package tools;

import javafx.scene.paint.Color;

public class Message implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	private String message;
	public String message() { return message; }
	private Color colour;
	public Color colour() { return colour; }
	
	public Message(String message, Color colour) {
		this.message = message;
		this.colour = colour;
	}
	public Message(String message) {
		this.message = message;
		this.colour = Color.WHITE;
	}

}
