package tools;

import javafx.scene.paint.Color;

public class Message {
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
