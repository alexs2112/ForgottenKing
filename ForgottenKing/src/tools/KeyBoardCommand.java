package tools;

import javafx.scene.input.KeyCode;

public class KeyBoardCommand {
	public KeyCode code;
	public char c;
	public boolean shift;
	
	public KeyBoardCommand(KeyCode code, char c, boolean shift) {
		this.code = code;
		this.c = c;
		this.shift = shift;
	}
}
